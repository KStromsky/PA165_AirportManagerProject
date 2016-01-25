/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.FlightCreationalDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.StewardDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightsAirplaneDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightArrivalDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDepartureDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightDestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightOriginDTO;
import cz.muni.fi.airportapi.dto.UpdateFlightStewardsDTO;
import cz.muni.fi.airportapi.facade.FlightFacade;
import cz.muni.fi.airportservicelayer.services.AirplaneService;
import cz.muni.fi.airportservicelayer.services.BeanMappingService;
import cz.muni.fi.airportservicelayer.services.DestinationService;
import cz.muni.fi.airportservicelayer.services.FlightService;
import cz.muni.fi.airportservicelayer.services.StewardService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Gabriela Podolnikova
 */
@Service
@Transactional
public class FlightFacadeImpl implements FlightFacade {

    @Autowired
    private FlightService flightService;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private AirplaneService airplaneService;

    @Autowired
    private StewardService stewardService;

    @Autowired
    private BeanMappingService beanMappingservice;

    @Override
    public FlightDTO getFlightWithId(Long id) {
        Flight flight = flightService.findById(id);
        if (flight == null) {
            return null;
        }
        return beanMappingservice.mapTo(flight, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        List<Flight> flights = flightService.findAll();
        return beanMappingservice.mapTo(flightService.findAll(), FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsByDate(boolean arrival) {
        return beanMappingservice.mapTo(flightService.listByDate(arrival), FlightDTO.class);
    }

    @Override
    public Long createFlight(FlightCreationalDTO f) {
        Flight mappedF = beanMappingservice.mapTo(f, Flight.class);
        for (Long stewardId : f.getStewardsIds()) {
            mappedF.addSteward(stewardService.findById(stewardId));
        }
        mappedF.setDestination(destinationService.findById(f.getDestinationId()));
        mappedF.setOrigin(destinationService.findById(f.getOriginId()));
        mappedF.setAirplane(airplaneService.findById(f.getAirplaneId()));
        flightService.create(mappedF);
        return mappedF.getId();
    }

    @Override
    public void removeFlight(Long id) {
        flightService.delete(flightService.findById(id));
    }

    @Override
    public void updateFlightArrival(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        f.setArrival(update.getArrival());
        flightService.update(f);
    }

    @Override
    public void updateFlightDeparture(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        f.setDeparture(update.getDeparture());
        flightService.update(f);
    }

    @Override
    public void updateFlightDestination(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        Destination d = destinationService.findById(update.getDestinationId());
        f.setDestination(d);
        flightService.update(f);
    }

    @Override
    public void updateFlightOrigin(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        Destination d = destinationService.findById(update.getOriginId());
        f.setOrigin(d);
        flightService.update(f);
    }

    @Override
    public void updateFlightAirplane(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        Airplane a = airplaneService.findById(update.getAirplaneId());
        f.setAirplane(a);
        flightService.update(f);
    }

    @Override
    public void updateFlightStewards(UpdateFlightDTO update) {
        Flight f = flightService.findById(update.getId());
        List<Long> stewardsIds = update.getStewardsIds();
        List<Steward> stewards = new ArrayList<>();
        for (Long id : stewardsIds) {
            Steward s = stewardService.findById(id);
            stewards.add(s);
        }
        f.setStewards(stewards);
        flightService.update(f);
    }

    @Override
    public UpdateFlightDTO getUpdateFlightWithId(Long id) {
        Flight flight = flightService.findById(id);
        if (flight == null) {
            return null;
        }
        UpdateFlightDTO update = beanMappingservice.mapTo(flight, UpdateFlightDTO.class);
        if (flight.getAirplane() != null) {
            update.setAirplaneId(flight.getAirplane().getId());
        }
        if (flight.getDestination()!= null) {
            update.setDestinationId(flight.getDestination().getId());
        }
        if (flight.getOrigin()!= null) {
            update.setOriginId(flight.getOrigin().getId());
        }
        
        if (flight.getStewards() != null) {
            List<Long> stewardsIds = new ArrayList<>();
            for (Steward steward : flight.getStewards()) {
                stewardsIds.add(steward.getId());
            }
            update.setStewardsIds(stewardsIds);
        }
        
        return update;
    }

    @Override
    public List<FlightDTO> getFlightsByOrigin(DestinationDTO origin) {
        return beanMappingservice.mapTo(flightService.listByOrigin(origin.getId()), FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getFlightsByDestination(DestinationDTO destination) {
        return beanMappingservice.mapTo(flightService.listByDestination(destination.getId()), FlightDTO.class);
    }

    @Override
    public boolean verifyAirplane(FlightDTO flight) {
        Flight entity = beanMappingservice.mapTo(flight, Flight.class);
        return flightService.verifyAirplane(entity);
    }

    @Override
    public boolean verifyStewards(FlightDTO flight) {
        Flight entity = beanMappingservice.mapTo(flight, Flight.class);
        return flightService.verifyStewards(entity);
    }
}
