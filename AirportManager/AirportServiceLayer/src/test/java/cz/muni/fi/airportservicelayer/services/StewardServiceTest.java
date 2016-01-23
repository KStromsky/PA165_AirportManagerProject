/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.dao.AirplaneDao;
import cz.muni.fi.airport.dao.DestinationDao;
import cz.muni.fi.airport.dao.StewardDao;
import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import cz.muni.fi.airportservicelayer.exceptions.BasicDataAccessException;
import cz.muni.fi.airportservicelayer.exceptions.IllegalArgumentDataException;
import cz.muni.fi.airportservicelayer.exceptions.ValidationDataException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.hibernate.service.spi.ServiceException;

import org.mockito.InjectMocks;
import org.mockito.*;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.util.Assert;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.fail;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sebastian Kupka
 */ 
@ContextConfiguration(classes=ServiceTestConfiguration.class)
public class StewardServiceTest extends AbstractTransactionalTestNGSpringContextTests  
{ 
    @Mock
    private StewardDao stewardDao;
    
    @Autowired
    @InjectMocks
    private StewardService stewardService;
    
    private Steward s1;
    private Steward s2;
    SimpleDateFormat formatter;
    
    @BeforeClass
    public void setup() throws ServiceException {
        MockitoAnnotations.initMocks(this);
    }
    
    @BeforeMethod
    public void prepare() {
        s1 = new Steward();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setPersonalIdentificator("123-12345");
        s1.setUsername("emaste");
        s1.setId(1l);
        
        s2 = new Steward();
        Date birth2 = null;
        Date employment2 = null;
        try {
            birth2 = formatter.parse("1985/02/08");
            employment2 = formatter.parse("2013/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s2.setDateOfBirth(birth2);
        s2.setEmploymentDate(employment2);
        s2.setFirstname("Peter");
        s2.setSurname("Malick");
        s2.setGender(Gender.MALE);
        s2.setPersonalIdentificator("123-23456");
        s2.setUsername("petmal");
        s2.setId(2l);
        
        List<Steward> stewards = new ArrayList<Steward>();
        stewards.add(s1);
        stewards.add(s2);
        
        when(stewardDao.findAll()).thenReturn(stewards);
        
        when(stewardDao.findById(2l)).thenReturn(s2);
        when(stewardDao.findById(1l)).thenReturn(s1);
        
        when(stewardDao.findByIdentificator("123-23456")).thenReturn(s2);
        when(stewardDao.findByIdentificator("123-12345")).thenReturn(s1);
        
        stewards = new ArrayList<Steward>();
        stewards.add(s1);
        
        when(stewardDao.findByName(s1.getFirstname(), s1.getSurname())).thenReturn(stewards);
        
        
        stewards = new ArrayList<Steward>();
        stewards.add(s2);
        
        when(stewardDao.findByName(s2.getFirstname(), s2.getSurname())).thenReturn(stewards);
    }
    
    @Test
    public void testCreate() {
        Assert.isNull(stewardService.createSteward(null, null));
        Assert.notNull(stewardService.createSteward(s1, "pw"));
    }

    @Test
    public void testFindById() {
        Steward steward= stewardService.findById(stewardService.createSteward(s1, "pw"));
        Assert.notNull(steward);
        assert steward.getPersonalIdentificator().equals(s1.getPersonalIdentificator());
        Assert.isNull(stewardService.findById(null));
    }

    @Test
    public void testFindByPersonalIdentificator() {
        Steward steward = stewardService.findById(stewardService.createSteward(s1, "pw"));
        Steward result = stewardService.findByPersonalIdentificator(s1.getPersonalIdentificator());
        assertEquals(steward, result);
        Assert.isNull(stewardService.findByPersonalIdentificator(null));
    }

    @Test
    public void testGetAllStewards() {
        List<Steward> expResult = new ArrayList<>();
        expResult.add(stewardService.findById(stewardService.createSteward(s1, "pw")));
        expResult.add(stewardService.findById(stewardService.createSteward(s2, "pw")));
        List<Steward> result = stewardService.findAllStewards();
        assertEquals(expResult, result);
    }

    @Test
    public void testFindByName() {
        Steward steward = stewardService.findById(stewardService.createSteward(s1, "pw"));
        List<Steward> result = stewardService.findByName(steward.getFirstname(), steward.getSurname());
        assert result.size() == 1;
        assert result.get(0).getFirstname().equals(steward.getFirstname());
        assert result.get(0).getSurname().equals(steward.getSurname());
        
        steward = stewardService.findById(stewardService.createSteward(s2, "pw"));
        result = stewardService.findByName(steward.getFirstname(), steward.getSurname());
        assert result.size() == 1;
        assert result.get(0).getFirstname().equals(steward.getFirstname());
        assert result.get(0).getSurname().equals(steward.getSurname());
        
        try {
            stewardService.findByName(steward.getFirstname(), null);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        try {
            stewardService.findByName(null, steward.getSurname());
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        try {
            stewardService.findByName(null, null);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
    }

    @Test
    public void testGetStewardFlights() {
        
        Airplane airplane1 = new Airplane();
        airplane1.setId(1L);
        airplane1.setCapacity(130);
        airplane1.setName("Phobos");
        airplane1.setType("JS-130");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 10, 1, 0);
        Date arrival2 = cal.getTime();
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane1);
        flight1.setDeparture(departure1);
        flight1.setArrival(arrival1);
        flight1.setOrigin(new Destination());
        flight1.addSteward(s1);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane1);
        flight2.setDeparture(departure2);
        flight2.setArrival(arrival2);
        flight2.setOrigin(new Destination());
        flight2.addSteward(s1);
        flight2.addSteward(s2);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane1);
        flight3.setDeparture(arrival1);
        flight3.setArrival(arrival2);
        flight3.setOrigin(new Destination());
        flight3.addSteward(s1);
        
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(new ArrayList<Flight>());
        
        assertEquals(stewardService.findStewardFlights(s1), new ArrayList<Flight>());
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        flights.add(flight2);
        flights.add(flight3);
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(flights);
        
        assertEquals(stewardService.findStewardFlights(s1), flights);
        
        flights = new ArrayList<>();
        flights.add(flight2);
        when(stewardDao.findLastStewardFlights(s2)).thenReturn(flights);
        
        
        assertEquals(stewardService.findStewardFlights(s2), flights);
        
        assertEquals(stewardService.findStewardFlights(null), new ArrayList<Flight>());
    }
    
    @Test
    public void testFindStewardLocation() {
        Airplane airplane1 = new Airplane();
        airplane1.setId(1L);
        airplane1.setCapacity(130);
        airplane1.setName("Phobos");
        airplane1.setType("JS-130");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane1);
        flight1.setDeparture(departure1);
        flight1.setArrival(arrival1);
        flight1.setOrigin(new Destination());
        flight1.addSteward(s1);
        
        
        Assert.isNull(stewardService.findStewardLocation(null));
        
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(new ArrayList<Flight>());
        Assert.isNull(stewardService.findStewardLocation(s1));
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight1);
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(flights);
        assertEquals(stewardService.findStewardLocation(s1), flight1.getDestination());
    }

    @Test
    public void testFindAvailableStewards() {
        Airplane airplane1 = new Airplane();
        airplane1.setId(1L);
        airplane1.setCapacity(130);
        airplane1.setName("Phobos");
        airplane1.setType("JS-130");
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 10, 1, 0);
        Date arrival2 = cal.getTime();
        
        Destination dest1 = new Destination(1l);
        dest1.setLocation("Brno");
        Destination dest2 = new Destination(2l);
        dest2.setLocation("Brno");
        Destination dest3 = new Destination(3l);
        dest3.setLocation("Bratislava");
        
        Flight flight1 = new Flight();
        flight1.setAirplane(airplane1);
        flight1.setDeparture(departure1);
        flight1.setArrival(arrival1);
        flight1.setOrigin(dest1);
        flight1.setDestination(dest2);
        flight1.addSteward(s1);
        
        Flight flight2 = new Flight();
        flight2.setAirplane(airplane1);
        flight2.setDeparture(departure2);
        flight2.setArrival(arrival2);
        flight2.setOrigin(dest2);
        flight2.setDestination(dest3);
        flight2.addSteward(s1);
        flight2.addSteward(s2);
        
        Flight flight3 = new Flight();
        flight3.setAirplane(airplane1);
        flight3.setDeparture(arrival1);
        flight3.setArrival(arrival2);
        flight3.setOrigin(dest3);
        flight3.setDestination(dest2);
        
        // data initialization finished
        
        // testing of finding free stewards in given time (and place)
        
        List<Steward> stewards = new ArrayList<Steward>();
        stewards.add(s2);
        
        List<Flight> flights = new ArrayList<>();
        flights.add(flight2);
        
        when(stewardDao.findAvailableStewards(departure1, arrival1)).thenReturn(stewards);
        when(stewardDao.findLastStewardFlights(s2)).thenReturn(flights);
        
        assertEquals(stewardService.findAvailableStewards(departure1, arrival1), stewards);
        assertEquals(stewardService.findSpecificStewards(departure1, arrival1, null), stewards);
        assertEquals(stewardService.findSpecificStewards(departure1, arrival1, dest3.getId()), stewards);
        assertEquals(stewardService.findSpecificStewards(departure1, arrival1, dest1.getId()), new ArrayList<Steward>());
        assertEquals(stewardService.findSpecificStewards(departure1, arrival1, dest2.getId()), new ArrayList<Steward>());
        
        // testing for invalid data
        try {
            stewardService.findAvailableStewards(departure1, null);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        try {
            stewardService.findAvailableStewards(null, arrival1);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        
        try {
            stewardService.findAvailableStewards(arrival1, departure1);
            fail("Inversed time accepted");
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        
        try {
            stewardService.findSpecificStewards(arrival1, departure1, null);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        
        try {
            stewardService.findSpecificStewards(arrival1, departure1, dest2.getId());
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        
        try {
            stewardService.findAvailableStewards(null, null);
            fail();
        } catch(IllegalArgumentException ex) {
            //valid;
        }
        
        // testing finding stewards at location
        
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(new ArrayList<Flight>());
        
        Assert.isNull(stewardService.findStewardLocation(null));
        Assert.isNull(stewardService.findStewardLocation(s1));
        assert !stewardService.findAvailableStewards(departure1, arrival1).contains(s1);
        
        flights = new ArrayList<>();
        flights.add(flight1);
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(flights);
        
        assertEquals(stewardService.findStewardLocation(s1), flight1.getDestination());
        
        // testing finding available stewards at given time and location
        
        stewards = new ArrayList<Steward>();
        stewards.add(s1);
        stewards.add(s2);
        
        flights = new ArrayList<>();
        flights.add(flight1);
        
        when(stewardDao.findAvailableStewards(arrival2, arrival2)).thenReturn(stewards);
        when(stewardDao.findLastStewardFlights(s1)).thenReturn(flights);
        
        assert stewardService.findSpecificStewards(arrival2, arrival2, null).contains(s1);
        assert stewardService.findSpecificStewards(arrival2, arrival2, null).contains(s2);
        
        assert stewardService.findSpecificStewards(arrival2, arrival2, dest2.getId()).contains(s1);
        assert !stewardService.findSpecificStewards(arrival2, arrival2, dest2.getId()).contains(s2);
        
        assert !stewardService.findSpecificStewards(arrival2, arrival2, dest3.getId()).contains(s1);
        assert stewardService.findSpecificStewards(arrival2, arrival2, dest3.getId()).contains(s2);
        
        assert !stewardService.findSpecificStewards(arrival2, arrival2, dest1.getId()).contains(s1);
        assert !stewardService.findSpecificStewards(arrival2, arrival2, dest1.getId()).contains(s2);
    }
    
    @Test(expectedExceptions = BasicDataAccessException.class)
    public void testThrowBasicDataAccesException() {
        when(stewardDao.update(s1)).thenThrow(new PersistenceException());
        stewardService.updateSteward(s1);
    } 
    
    @Test(expectedExceptions = IllegalArgumentDataException.class)
    public void testThrowInvallidArgumentDataException() {
        when(stewardDao.update(s2)).thenThrow(new IllegalArgumentException());
        stewardService.updateSteward(s2);
    }
    
    @Test(expectedExceptions = ValidationDataException.class)
    public void testThrowConstrainViolationDataException() {
        when(stewardDao.findById(3L)).thenThrow(new ValidationException());
        stewardService.findById(3L);
    }
    
    
} 
