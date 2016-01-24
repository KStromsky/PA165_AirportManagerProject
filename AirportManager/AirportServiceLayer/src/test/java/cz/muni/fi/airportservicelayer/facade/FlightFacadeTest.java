/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.enums.Gender;
import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneDTO;
import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.dto.StewardDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightArrivalDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDepartureDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightOriginDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightsAirplaneDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.FlightFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author Gabi
 */
@ContextConfiguration(classes = {FacadeTestConfiguration.class})
public class FlightFacadeTest extends AbstractTransactionalTestNGSpringContextTests {

    @Autowired
    private FlightFacade flightFacade;
    
    @Autowired
    private AirplaneFacade airplaneFacade;
    
    @Autowired
    private DestinationFacade destinationFacade;
    
    @Autowired
    private StewardFacade stewardFacade;
    

    private FlightCreationalDTO flightCreationalDTO1;
    private FlightCreationalDTO flightCreationalDTO2;

    private FlightDTO flightDTO;

    private StewardCreationalDTO s1;
    private StewardCreationalDTO s2;
    private StewardCreationalDTO s3;
    private DestinationCreationalDTO d1;
    private DestinationCreationalDTO d2;
    private AirplaneCreationalDTO a1;
    private AirplaneCreationalDTO a2;

    private Date date1;
    private Date date2;
    private Date date3;
    
    private DestinationDTO dest1;
    private DestinationDTO dest2;
    
    private StewardDTO stew1;
    private StewardDTO stew2;
    private StewardDTO stew3;
    
    private AirplaneDTO air1;
    private AirplaneDTO air2;

    @BeforeMethod
    public void setup() throws ParseException {
        
        flightCreationalDTO1 = new FlightCreationalDTO();

        flightCreationalDTO2 = new FlightCreationalDTO();
        
        flightDTO = new FlightDTO();

        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 1, 2, 0);
        date1 = cal.getTime();
        cal.set(2015, 1, 1, 12, 30);
        date2 = cal.getTime();
        cal.set(2015, 1, 2, 0, 0);
        date3 = cal.getTime();

        a1 = new AirplaneCreationalDTO();
        a1.setCapacity(1);
        a1.setName("airA");
        a1.setType("typeA");

        a2 = new AirplaneCreationalDTO();
        a2.setCapacity(2);
        a2.setName("airB");
        a2.setType("typeB");
        
        air1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(a1));
        air2 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(a2));

        d1 = new DestinationCreationalDTO();
        d1.setLocation("Brno");

        d2 = new DestinationCreationalDTO();
        d2.setLocation("Praha");
        
        dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(d1));
        dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(d2));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");

        s1 = new StewardCreationalDTO();
        s1.setFirstname("fn1");
        s1.setSurname("sn1");
        s1.setPersonalIdentificator("111-11111");
        s1.setDateOfBirth(formatter.parse("1988/02/02"));
        s1.setEmploymentDate(formatter.parse("2014/03/01"));
        s1.setUsername("bbb");
        s1.setPassword("pw");
        s1.setGender(Gender.MALE);

        s2 = new StewardCreationalDTO();
        s2.setFirstname("fn2");
        s2.setSurname("sn1");
        s2.setPersonalIdentificator("111-11112");
        s2.setDateOfBirth(formatter.parse("1988/02/02"));
        s2.setEmploymentDate(formatter.parse("2014/03/01"));
        s2.setUsername("aaaa");
        s2.setPassword("pw");
        s2.setGender(Gender.MALE);

        s3 = new StewardCreationalDTO();
        s3.setFirstname("fn2");
        s3.setSurname("sn3");
        s3.setPersonalIdentificator("111-11113");
        s3.setDateOfBirth(formatter.parse("1988/02/02"));
        s3.setEmploymentDate(formatter.parse("2014/03/01"));
        s3.setUsername("addd");
        s3.setPassword("pw");
        s3.setGender(Gender.FEMALE);
        
        //Long s1id = 0L;
        //s1id = stewardFacade.createSteward(s1);
        //System.out.println("ID STEWARDA JE" + s1id);
        stew1 = stewardFacade.getStewardWithId(stewardFacade.createSteward(s1));
        stew2 = stewardFacade.getStewardWithId(stewardFacade.createSteward(s2));
        stew3 = stewardFacade.getStewardWithId(stewardFacade.createSteward(s3));
        
        flightCreationalDTO1.setAirplaneId(air1.getId());
        flightCreationalDTO1.setArrival(date2);
        flightCreationalDTO1.setDeparture(date1);
        flightCreationalDTO1.setOriginId(dest1.getId());
        flightCreationalDTO1.setDestinationId(dest2.getId());
        flightCreationalDTO1.addStewardIds(stew1.getId());
        flightCreationalDTO1.addStewardIds(stew2.getId());
        
        flightCreationalDTO2.setAirplaneId(air2.getId());
        flightCreationalDTO2.setArrival(date3);
        flightCreationalDTO2.setDeparture(date2);
        flightCreationalDTO2.setOriginId(dest2.getId());
        flightCreationalDTO2.setDestinationId(dest1.getId());
        flightCreationalDTO2.addStewardIds(stew1.getId());
        
        flightDTO.setAirplane(air1);
        flightDTO.setArrival(date2);
        flightDTO.setDeparture(date1);
        flightDTO.setOrigin(dest1);
        flightDTO.setDestination(dest2);
        flightDTO.addSteward(stew1);
        flightDTO.addSteward(stew2);
    }
    
    @Test
    public void testGetFlightWithId() {
        FlightDTO fDTO = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreationalDTO1));
        Assert.assertEquals(fDTO, flightDTO);
    }
    
//    public List<FlightDTO> getAllFlights();
    @Test
    public void testGetAllFlights(){
        flightFacade.createFlight(flightCreationalDTO1);
        List<FlightDTO> l1 = flightFacade.getAllFlights();
        List<FlightDTO> l2 = new ArrayList<>();
        l2.add(flightDTO);
        Assert.assertEquals(l1, l2);
    }
    
//    public List<FlightDTO> getFlightsByDate(boolean arrival);
    @Test
    public void testGetFlightsByDate() {
        flightFacade.createFlight(flightCreationalDTO1);
        flightFacade.createFlight(flightCreationalDTO2);
        //flight should be ordered by arrival
        List<FlightDTO> l1 = flightFacade.getFlightsByDate(true);
        Assert.assertEquals(l1.get(0), flightDTO);
        assert flightDTO.equals(l1.get(0));
    }
    
    @Test
    public void testCreateFlight() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        Assert.assertNotNull(id);
    }
//    public void removeFlight(Long id);
    @Test
    public void testRemoveFlight() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        flightFacade.removeFlight(id);
        List<FlightDTO> l = flightFacade.getAllFlights();
        assert l.isEmpty();
    }
//    public void updateFlightArrival(UpdateFlightArrivalDTO update);
    @Test
    public void testUpdateFlightArrival() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        UpdateFlightArrivalDTO update = new UpdateFlightArrivalDTO();
        update.setId(id);
        update.setArrival(date3);
        flightFacade.updateFlightArrival(update);
        assert date3.equals(flightFacade.getFlightWithId(id).getArrival());
    }
//    public void updateFlightDeparture(UpdateFlightDepartureDTO update);
    @Test
    public void testUpdateFlightDeparture() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        UpdateFlightDepartureDTO update = new UpdateFlightDepartureDTO();
        update.setId(id);
        update.setDeparture(date3);
        flightFacade.updateFlightDeparture(update);
        assert date3.equals(flightFacade.getFlightWithId(id).getDeparture());
    }
//    public void updateFlightDestination(UpdateFlightDestinationDTO update);
    @Test
    public void testUpdateFlightDestination() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        UpdateFlightDestinationDTO update = new UpdateFlightDestinationDTO();
        update.setId(id);
        update.setDestination(dest1);
        flightFacade.updateFlightDestination(update);
        assert dest1.equals(flightFacade.getFlightWithId(id).getDestination());
    }
//    public void updateFlightOrigin(UpdateFlightOriginDTO update);
    @Test
    public void testUpdateFlightOrigin() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        UpdateFlightOriginDTO update = new UpdateFlightOriginDTO();
        update.setId(id);
        update.setOrigin(dest2);
        flightFacade.updateFlightOrigin(update);
        assert dest2.equals(flightFacade.getFlightWithId(id).getOrigin());
    }
//    public void updateFlightAirplane(UpdateFlightsAirplaneDTO update);
    @Test
    public void testUpdateFlightAirplane() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        UpdateFlightsAirplaneDTO update = new UpdateFlightsAirplaneDTO();
        update.setId(id);
        update.setAirplane(air2);
        flightFacade.updateFlightAirplane(update);
        FlightDTO f = flightFacade.getFlightWithId(id);
        AirplaneDTO a = flightFacade.getFlightWithId(id).getAirplane();
        assertEquals(air2, flightFacade.getFlightWithId(id).getAirplane());
    }
//    public List<FlightDTO> getFlightsByOrigin(Destination origin);
    @Test
    public void testGetFlightsByOrigin() {
        Long id = flightFacade.createFlight(flightCreationalDTO1);
        List<FlightDTO> l1 = flightFacade.getFlightsByOrigin(dest1);
        List<FlightDTO> l2 = new ArrayList<>();
        l2.add(flightDTO);
        Assert.assertEquals(l1, l2);
    }
//    public List<FlightDTO> getFlightsByDestination(Destination destination);
    @Test
    public void testGetFlightsByDestination() {
        flightFacade.createFlight(flightCreationalDTO1);
        List<FlightDTO> l1 = flightFacade.getFlightsByDestination(dest2);
        List<FlightDTO> l2 = new ArrayList<>();
        l2.add(flightDTO);
        Assert.assertEquals(l1, l2);
    }
}
