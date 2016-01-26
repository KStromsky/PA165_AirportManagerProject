/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.dao.AirplaneDao;
import cz.muni.fi.airport.dao.DestinationDao;
import cz.muni.fi.airport.dao.FlightDao;
import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneDTO;
import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.UpdateAirplaneCapacityDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportapi.facade.FlightFacade;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.config.FacadeTestConfiguration;
import cz.muni.fi.airportservicelayer.config.ServiceTestConfiguration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * 
 * @author Kuba
 */
@ContextConfiguration(classes = {FacadeTestConfiguration.class})
public class AirplaneFacadeTest extends AbstractTransactionalTestNGSpringContextTests {
    
    @Autowired
    private AirplaneFacade airplaneFacade;
    
    @Autowired
    private FlightFacade flightFacade;
    
    @Autowired
    private DestinationFacade destinationFacade;
    
    private AirplaneCreationalDTO creationalAirplaneDTO1;
    private AirplaneCreationalDTO creationalAirplaneDTO2;
    private AirplaneDTO airplaneDTO;
    
    @BeforeMethod
    public void setup() {
        creationalAirplaneDTO1 = new AirplaneCreationalDTO();
        creationalAirplaneDTO1.setCapacity(100);
        creationalAirplaneDTO1.setName("Phobos");
        creationalAirplaneDTO1.setType("JS-100");
        
        creationalAirplaneDTO2 = new AirplaneCreationalDTO();
        creationalAirplaneDTO2.setCapacity(50);
        creationalAirplaneDTO2.setName("Deimos");
        creationalAirplaneDTO2.setType("JS-50");
        
        airplaneDTO = new AirplaneDTO();
        airplaneDTO.setName("Phobos");
        airplaneDTO.setType("JS-100");
        airplaneDTO.setCapacity(100);
        
        
    }
    
    @Test
    public void testGetAirplaneWithId() {
        AirplaneDTO aDTO = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(creationalAirplaneDTO1));
        Assert.assertEquals(aDTO, airplaneDTO);
    }

    @Test
    public void testGetAllAirplanes() {
        airplaneFacade.createAirplane(creationalAirplaneDTO1);
        List<AirplaneDTO> l1 = airplaneFacade.getAllAirplanes();
        List<AirplaneDTO> l2 = new ArrayList<>();
        l2.add(airplaneDTO);
        Assert.assertEquals(l1, l2);
    }

    @Test
    public void testGetAirplaneWithName() {
        airplaneFacade.createAirplane(creationalAirplaneDTO1);
        List<AirplaneDTO> l1 = airplaneFacade.getAirplaneWithName("Phobos");
        List<AirplaneDTO> l2 = new ArrayList<>();
        l2.add(airplaneDTO);
        Assert.assertEquals(l1, l2);
    }

    @Test
    public void testCreateAirplane() {
        Long id = airplaneFacade.createAirplane(creationalAirplaneDTO1);
        Assert.assertNotNull(id);
    }

    @Test
    public void testRemoveAirplane() {
        Long id = airplaneFacade.createAirplane(creationalAirplaneDTO1);
        airplaneFacade.removeAirplane(id);
        List<AirplaneDTO> l = airplaneFacade.getAllAirplanes();
        assert l.isEmpty();
    }

    @Test
    public void testUpdateAirplaneCapacity() {
        Long id = airplaneFacade.createAirplane(creationalAirplaneDTO1);
        UpdateAirplaneCapacityDTO update = new UpdateAirplaneCapacityDTO();
        update.setId(id);
        update.setCapacity(120);
        airplaneFacade.updateAirplaneCapacity(update);
        assert 120 == airplaneFacade.getAirplaneWithId(id).getCapacity();
    }

    @Test
    public void testGetAvailableAirplanes() {
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date from = cal.getTime();
        cal.set(2015, 1, 15, 0, 0);
        Date to = cal.getTime();
        
        Long id = airplaneFacade.createAirplane(creationalAirplaneDTO1);
        List<AirplaneDTO> l1 = airplaneFacade.getAvailableAirplanes(from, to);
        List<AirplaneDTO> l2 = new ArrayList<>();
        l2.add(airplaneDTO);
        Assert.assertEquals(l1, l2);
    }

    @Test
    public void testGetAirplaneFlights() {     
        
        AirplaneDTO airplane1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(creationalAirplaneDTO1));
        AirplaneDTO airplane2 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(creationalAirplaneDTO2));
        
        DestinationCreationalDTO destCreate1 = new DestinationCreationalDTO();
        destCreate1.setLocation("Brno");
        DestinationCreationalDTO destCreate2 = new DestinationCreationalDTO();
        destCreate2.setLocation("Praha");
        DestinationCreationalDTO destCreate3 = new DestinationCreationalDTO();
        destCreate3.setLocation("Bratislava");
        
        DestinationDTO dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate1));
        DestinationDTO dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate2));
        DestinationDTO dest3 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate3));
        
        Calendar cal = Calendar.getInstance();
        cal.set(2015, 1, 8, 0, 0);
        Date departure1 = cal.getTime();
        cal.set(2015, 1, 9, 0, 0);
        Date arrival1 = cal.getTime();
        
        cal.set(2015, 1, 10, 0, 0);
        Date departure2 = cal.getTime();
        cal.set(2015, 1, 10, 1, 0);
        Date arrival2 = cal.getTime();
        
        cal.set(2015, 1, 11, 1, 0);
        Date departure3 = cal.getTime();
        cal.set(2015, 1, 11, 1, 2);
        Date arrival3 = cal.getTime();
        
        cal.set(2015, 1, 11, 1, 1);
        Date between3 = cal.getTime();
        
        FlightCreationalDTO flightCreate1 = new FlightCreationalDTO();
        flightCreate1.setAirplaneId(airplane1.getId());
        flightCreate1.setDeparture(departure1);
        flightCreate1.setArrival(arrival1);
        flightCreate1.setOriginId(dest1.getId());
        flightCreate1.setDestinationId(dest2.getId());
        
        FlightCreationalDTO flightCreate2 = new FlightCreationalDTO();
        flightCreate2.setAirplaneId(airplane2.getId());
        flightCreate2.setDeparture(departure2);
        flightCreate2.setArrival(arrival2);
        flightCreate2.setOriginId(dest2.getId());
        flightCreate2.setDestinationId(dest3.getId());
        
        FlightCreationalDTO flightCreate3 = new FlightCreationalDTO();
        flightCreate3.setAirplaneId(airplane1.getId());
        flightCreate3.setDeparture(departure3);
        flightCreate3.setArrival(arrival3);
        flightCreate3.setOriginId(dest3.getId());
        flightCreate3.setDestinationId(dest2.getId());
        
        FlightDTO flight1 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate1));
        FlightDTO flight2 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate2));
        FlightDTO flight3 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate3));
        
        // data prepared - starting test
        
        List<FlightDTO> flights = new ArrayList<FlightDTO>();
        flights.add(flight3);
        flights.add(flight1);
        
        Assert.assertEquals(airplaneFacade.getAirplaneFlights(airplane1.getId()), flights);
        
        flights = new ArrayList<FlightDTO>();
        flights.add(flight2);
        
        Assert.assertEquals(airplaneFacade.getAirplaneFlights(airplane2.getId()), flights);
    }

    @Test
    public void testGetSpecificAirplanes() {
        AirplaneDTO airplane1 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(creationalAirplaneDTO1));
        AirplaneDTO airplane2 = airplaneFacade.getAirplaneWithId(airplaneFacade.createAirplane(creationalAirplaneDTO2));
        
        DestinationCreationalDTO destCreate1 = new DestinationCreationalDTO();
        destCreate1.setLocation("Brno");
        DestinationCreationalDTO destCreate2 = new DestinationCreationalDTO();
        destCreate2.setLocation("Praha");
        DestinationCreationalDTO destCreate3 = new DestinationCreationalDTO();
        destCreate3.setLocation("Bratislava");
        
        DestinationDTO dest1 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate1));
        DestinationDTO dest2 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate2));
        DestinationDTO dest3 = destinationFacade.getDestinationWithId(destinationFacade.createDestination(destCreate3));
        
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
        cal.set(2015, 1, 13, 1, 0);
        Date arrival3 = cal.getTime();
        
        cal.set(2015, 1, 14, 3, 0);
        Date after = cal.getTime();
        
        
        FlightCreationalDTO flightCreate1 = new FlightCreationalDTO();
        flightCreate1.setAirplaneId(airplane1.getId());
        flightCreate1.setDeparture(departure1);
        flightCreate1.setArrival(arrival1);
        flightCreate1.setOriginId(dest1.getId());
        flightCreate1.setDestinationId(dest2.getId());
        
        FlightCreationalDTO flightCreate2 = new FlightCreationalDTO();
        flightCreate2.setAirplaneId(airplane1.getId());
        flightCreate2.setDeparture(departure2);
        flightCreate2.setArrival(arrival2);
        flightCreate2.setOriginId(dest2.getId());
        flightCreate2.setDestinationId(dest3.getId());
        
        FlightCreationalDTO flightCreate3 = new FlightCreationalDTO();
        flightCreate3.setAirplaneId(airplane2.getId());
        flightCreate3.setDeparture(departure3);
        flightCreate3.setArrival(arrival3);
        flightCreate3.setOriginId(dest3.getId());
        flightCreate3.setDestinationId(dest2.getId());
        
        FlightDTO flight1 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate1));
        FlightDTO flight2 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate2));
        FlightDTO flight3 = flightFacade.getFlightWithId(flightFacade.createFlight(flightCreate3));
        
        // data prepared - starting test
        
        Assert.assertEquals(airplaneFacade.getAllAirplanes().size(), 2);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 0, null).size(), 2);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 50, null).size(), 2);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 70, null).size(), 1);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 110, null).size(), 0);
        
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(departure1, after, 0, null).size(), 0);
        
        List<AirplaneDTO> airplanes = new ArrayList<AirplaneDTO>();
        airplanes.add(airplane2);
        
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 0, dest2.getLocation()), airplanes);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(departure1, arrival2, 0, null), airplanes);
        
        airplanes = new ArrayList<AirplaneDTO>();
        airplanes.add(airplane1);
        
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(after, after, 0, dest3.getLocation()), airplanes);
        Assert.assertEquals(airplaneFacade.getSpecificAirplanes(arrival3, after, 0, null), airplanes);
        
    }
}