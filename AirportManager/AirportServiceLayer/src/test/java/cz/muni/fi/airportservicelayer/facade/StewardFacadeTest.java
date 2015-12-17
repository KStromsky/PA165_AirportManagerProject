/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportapi.dto.*;
import cz.muni.fi.airportapi.facade.*;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Sebastian Kupka
 */ 
@ContextConfiguration(classes = {FacadeTestConfiguration.class})
public class StewardFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private StewardFacade stewardFacade;
    
    @Autowired
    private FlightFacade flightFacade;
    
    @Autowired
    private DestinationFacade destinationFacade;
    
    @Autowired
    private AirplaneFacade airplaneFacade;
    
    private StewardCreationalDTO s1;
    private StewardCreationalDTO s2;
    SimpleDateFormat formatter;
    
    @BeforeMethod
    public void setUp() {
        s1 = new StewardCreationalDTO();
        formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date birth = null;
        Date employment = null;
        try {
            birth = formatter.parse("1988/02/02");
            employment = formatter.parse("2014/03/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s1.setDateOfBirth(birth);
        s1.setEmploymentDate(employment);
        s1.setFirstname("Emma");
        s1.setSurname("Stevenson");
        s1.setGender(Gender.FEMALE);
        s1.setPersonalIdentificator("123-12345");
        
        s2 = new StewardCreationalDTO();
        Date birth2 = null;
        Date employment2 = null;
        try {
            birth2 = formatter.parse("1985/02/08");
            employment2 = formatter.parse("2013/04/01");
        } catch (ParseException ex) {
            Logger.getLogger(StewardFacadeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        s2.setDateOfBirth(birth2);
        s2.setEmploymentDate(employment2);
        s2.setFirstname("Peter");
        s2.setSurname("Malick");
        s2.setGender(Gender.MALE);
        s2.setPersonalIdentificator("123-23456");
    }

    @Test
    public void testGetStewardWithId() {
        StewardDTO steward= stewardFacade.getStewardWithId(stewardFacade.createSteward(s1));
        Assert.notNull(steward);
        assert steward.getPersonalIdentificator().equals(s1.getPersonalIdentificator());
        
    }

    @Test
    public void testGetStewardWithPersonalIdentificator() {
        StewardDTO steward = stewardFacade.getStewardWithId(stewardFacade.createSteward(s1));
        StewardDTO result = stewardFacade.getStewardWithPersonalIdentificator(s1.getPersonalIdentificator());
        assertEquals(steward, result);
        
    }

    @Test
    public void testGetAllStewards() {
        List<StewardDTO> expResult = new ArrayList<>();
        expResult.add(stewardFacade.getStewardWithId(stewardFacade.createSteward(s1)));
        expResult.add(stewardFacade.getStewardWithId(stewardFacade.createSteward(s2)));
        List<StewardDTO> result = stewardFacade.getAllStewards();
        assertEquals(expResult, result);
        
    }

    @Test
    public void testGetStewardWithName() {
        s1.setFirstname("None");
        s2.setFirstname("FirstName");
        s2.setSurname("Surname");
        stewardFacade.createSteward(s1);
        StewardDTO steward = stewardFacade.getStewardWithId(stewardFacade.createSteward(s2));
        List<StewardDTO> result = stewardFacade.getStewardWithName("FirstName", "Surname");
        assert result.size() == 1;
        assert result.get(0).getFirstname().equals(steward.getFirstname());
        assert result.get(0).getSurname().equals(steward.getSurname());
    }

    @Test
    public void testCreateSteward() {
        Long id = stewardFacade.createSteward(s1);
        StewardDTO steward = stewardFacade.getStewardWithId(id);
        Assert.notNull(steward);
        assert steward.getPersonalIdentificator().equals(s1.getPersonalIdentificator()); 
    }

    @Test
    public void testRemoveSteward() {
        Long id = stewardFacade.createSteward(s1);
        Assert.notNull(stewardFacade.getStewardWithId(id));
        stewardFacade.removeSteward(id);
        StewardDTO tmp = stewardFacade.getStewardWithId(id);
        Assert.isNull(tmp);
        
    }

    @Test
    public void testUpdateStewardName() {
        Long id = stewardFacade.createSteward(s1);
        
        UpdateStewardNameDTO update = new UpdateStewardNameDTO();
        update.setFirstname(s1.getFirstname() + " New");
        update.setSurname(s1.getSurname()+ " New");
        update.setId(id);
        
        StewardDTO steward = stewardFacade.getStewardWithId(id); 
        assert steward.getFirstname().equals(s1.getFirstname());
        assert steward.getSurname().equals(s1.getSurname());
        
        stewardFacade.updateStewardName(update);
        
        steward = stewardFacade.getStewardWithId(id); 
        assert steward.getFirstname().equals(s1.getFirstname() + " New");
        assert steward.getSurname().equals(s1.getSurname()+ " New"); 
        
    }
    
    @Test
    public void testGetStewardFlights() {
        
        Long stewardId1 = stewardFacade.createSteward(s1);   
        StewardDTO steward1 = stewardFacade.getStewardWithId(stewardId1);
        
        Long stewardId2 = stewardFacade.createSteward(s2);   
        StewardDTO steward2 = stewardFacade.getStewardWithId(stewardId2);  
        
        DestinationCreationalDTO destCreate1 = new DestinationCreationalDTO();
        destCreate1.setLocation("Brno");
        DestinationCreationalDTO destCreate2 = new DestinationCreationalDTO();
        destCreate2.setLocation("Praha");
        DestinationCreationalDTO destCreate3 = new DestinationCreationalDTO();
        destCreate3.setLocation("Bratislava");
        
        DestinationDTO dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate1));
        DestinationDTO dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate2));
        DestinationDTO dest3 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate3));
        
        AirplaneCreationalDTO airplaneCreate1 = new AirplaneCreationalDTO();
        airplaneCreate1.setCapacity(130);
        airplaneCreate1.setName("Phobos");
        airplaneCreate1.setType("JS-130");
        
        AirplaneDTO airplane1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(airplaneCreate1));
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 11, 1, 0);
        Date arrival2 = cal.getTime();
        
        cal.set(2015, 1, 12, 1, 0);
        Date departure3 = cal.getTime();
        cal.set(2015, 1, 13, 1, 1);
        Date arrival3 = cal.getTime();
        
        FlightCreationalDTO flightCreate1 = new FlightCreationalDTO();
        flightCreate1.setAirplaneId(airplane1.getId());
        flightCreate1.setDeparture(departure1);
        flightCreate1.setArrival(arrival1);
        flightCreate1.setOriginId(dest1.getId());
        flightCreate1.setDestinationId(dest2.getId());
        flightCreate1.addStewardIds(steward1.getId());
        
        FlightCreationalDTO flightCreate2 = new FlightCreationalDTO();
        flightCreate2.setAirplaneId(airplane1.getId());
        flightCreate2.setDeparture(departure2);
        flightCreate2.setArrival(arrival2);
        flightCreate2.setOriginId(dest2.getId());
        flightCreate2.setDestinationId(dest3.getId());
        flightCreate2.addStewardIds(steward1.getId());
        flightCreate2.addStewardIds(steward2.getId());
        
        FlightCreationalDTO flightCreate3 = new FlightCreationalDTO();
        flightCreate3.setAirplaneId(airplane1.getId());
        flightCreate3.setDeparture(departure3);
        flightCreate3.setArrival(arrival3);
        flightCreate3.setOriginId(dest3.getId());
        flightCreate3.setDestinationId(dest2.getId());
        flightCreate3.addStewardIds(steward1.getId());
        
        FlightDTO flight1 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate1));
        FlightDTO flight2 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate2));
        FlightDTO flight3 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate3));
        
        // data prepared - starting test
        
        List<FlightDTO> flights = new ArrayList<FlightDTO>();
        flights.add(flight3);
        flights.add(flight2);
        flights.add(flight1);
        
        assertEquals(stewardFacade.getStewardFlights(stewardId1), flights);
        
        flights = new ArrayList<FlightDTO>();
        flights.add(flight2);
        
        assertEquals(stewardFacade.getStewardFlights(stewardId2), flights);
    }

    @Test
    public void testGetStewardAtLocation() {
        Long stewardId1 = stewardFacade.createSteward(s1);   
        StewardDTO steward1 = stewardFacade.getStewardWithId(stewardId1);
        
        Long stewardId2 = stewardFacade.createSteward(s2);   
        StewardDTO steward2 = stewardFacade.getStewardWithId(stewardId2);  
        
        DestinationCreationalDTO destCreate1 = new DestinationCreationalDTO();
        destCreate1.setLocation("Brno");
        DestinationCreationalDTO destCreate2 = new DestinationCreationalDTO();
        destCreate2.setLocation("Praha");
        DestinationCreationalDTO destCreate3 = new DestinationCreationalDTO();
        destCreate3.setLocation("Bratislava");
        
        DestinationDTO dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate1));
        DestinationDTO dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate2));
        DestinationDTO dest3 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate3));
        
        AirplaneCreationalDTO airplaneCreate1 = new AirplaneCreationalDTO();
        airplaneCreate1.setCapacity(130);
        airplaneCreate1.setName("Phobos");
        airplaneCreate1.setType("JS-130");
        
        AirplaneDTO airplane1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(airplaneCreate1));
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 11, 1, 0);
        Date arrival2 = cal.getTime();
        
        cal.set(2015, 1, 12, 1, 0);
        Date departure3 = cal.getTime();
        cal.set(2015, 1, 13, 1, 1);
        Date arrival3 = cal.getTime();
        
        FlightCreationalDTO flightCreate1 = new FlightCreationalDTO();
        flightCreate1.setAirplaneId(airplane1.getId());
        flightCreate1.setDeparture(departure1);
        flightCreate1.setArrival(arrival1);
        flightCreate1.setOriginId(dest1.getId());
        flightCreate1.setDestinationId(dest2.getId());
        flightCreate1.addStewardIds(steward1.getId());
        
        FlightCreationalDTO flightCreate2 = new FlightCreationalDTO();
        flightCreate2.setAirplaneId(airplane1.getId());
        flightCreate2.setDeparture(departure2);
        flightCreate2.setArrival(arrival2);
        flightCreate2.setOriginId(dest2.getId());
        flightCreate2.setDestinationId(dest3.getId());
        flightCreate2.addStewardIds(steward1.getId());
        flightCreate2.addStewardIds(steward2.getId());
        
        FlightCreationalDTO flightCreate3 = new FlightCreationalDTO();
        flightCreate3.setAirplaneId(airplane1.getId());
        flightCreate3.setDeparture(departure3);
        flightCreate3.setArrival(arrival3);
        flightCreate3.setOriginId(dest3.getId());
        flightCreate3.setDestinationId(dest2.getId());
        flightCreate3.addStewardIds(steward1.getId());
        
        FlightDTO flight1 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate1));
        FlightDTO flight2 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate2));
        FlightDTO flight3 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate3));
        
        // data prepared - starting test
        
        List<StewardDTO> stewards = new ArrayList<StewardDTO>();
        stewards.add(steward2);
        
        assertEquals(stewardFacade.getAvailableStewardsAtLocation(dest3.getId()), stewards);
        
        stewards = new ArrayList<StewardDTO>();
        stewards.add(steward1);
        
        assertEquals(stewardFacade.getAvailableStewardsAtLocation(dest2.getId()), stewards);
        
        stewards = new ArrayList<StewardDTO>();
        
        assertEquals(stewardFacade.getAvailableStewardsAtLocation(dest1.getId()), stewards);
    }
    
    @Test
    public void testFindSpecificStewards() {
        Long stewardId1 = stewardFacade.createSteward(s1);   
        StewardDTO steward1 = stewardFacade.getStewardWithId(stewardId1);
        
        Long stewardId2 = stewardFacade.createSteward(s2);   
        StewardDTO steward2 = stewardFacade.getStewardWithId(stewardId2);  
        
        DestinationCreationalDTO destCreate1 = new DestinationCreationalDTO();
        destCreate1.setLocation("Brno");
        DestinationCreationalDTO destCreate2 = new DestinationCreationalDTO();
        destCreate2.setLocation("Praha");
        DestinationCreationalDTO destCreate3 = new DestinationCreationalDTO();
        destCreate3.setLocation("Bratislava");
        
        DestinationDTO dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate1));
        DestinationDTO dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate2));
        DestinationDTO dest3 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate3));
        
        AirplaneCreationalDTO airplaneCreate1 = new AirplaneCreationalDTO();
        airplaneCreate1.setCapacity(130);
        airplaneCreate1.setName("Phobos");
        airplaneCreate1.setType("JS-130");
        
        AirplaneDTO airplane1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(airplaneCreate1));
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 11, 1, 0);
        Date arrival2 = cal.getTime();
        
        cal.set(2015, 1, 12, 1, 0);
        Date departure3 = cal.getTime();
        cal.set(2015, 1, 13, 1, 1);
        Date arrival3 = cal.getTime();
        cal.set(2015, 1, 14, 1, 2);
        
        Date last = cal.getTime();
        
        
        FlightCreationalDTO flightCreate1 = new FlightCreationalDTO();
        flightCreate1.setAirplaneId(airplane1.getId());
        flightCreate1.setDeparture(departure1);
        flightCreate1.setArrival(arrival1);
        flightCreate1.setOriginId(dest1.getId());
        flightCreate1.setDestinationId(dest2.getId());
        flightCreate1.addStewardIds(steward1.getId());
        
        FlightCreationalDTO flightCreate2 = new FlightCreationalDTO();
        flightCreate2.setAirplaneId(airplane1.getId());
        flightCreate2.setDeparture(departure2);
        flightCreate2.setArrival(arrival2);
        flightCreate2.setOriginId(dest2.getId());
        flightCreate2.setDestinationId(dest3.getId());
        flightCreate2.addStewardIds(steward1.getId());
        flightCreate2.addStewardIds(steward2.getId());
        
        FlightCreationalDTO flightCreate3 = new FlightCreationalDTO();
        flightCreate3.setAirplaneId(airplane1.getId());
        flightCreate3.setDeparture(departure3);
        flightCreate3.setArrival(arrival3);
        flightCreate3.setOriginId(dest3.getId());
        flightCreate3.setDestinationId(dest2.getId());
        flightCreate3.addStewardIds(steward1.getId());
        
        FlightDTO flight1 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate1));
        FlightDTO flight2 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate2));
        FlightDTO flight3 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate3));
        
        // data prepared - starting test
        
        assert stewardFacade.findSpecificStewards(last, last, null).size() == 2;
        assert stewardFacade.findSpecificStewards(last, last, dest1.getId()).isEmpty();
        
        assert stewardFacade.findSpecificStewards(departure1, last, null).isEmpty();
        
        List<StewardDTO> stewards = new ArrayList<StewardDTO>();
        stewards.add(steward2);
        
        assertEquals(stewardFacade.findSpecificStewards(last, last, dest3.getId()), stewards);
        
        stewards = new ArrayList<StewardDTO>();
        stewards.add(steward1);
        
        assertEquals(stewardFacade.findSpecificStewards(last, last, dest2.getId()), stewards);
    }
} 