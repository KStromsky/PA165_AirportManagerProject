/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.dao.AirplaneDao;
import cz.muni.fi.airport.dao.FlightDao;
import cz.muni.fi.airport.dao.StewardDao;
import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airportservicelayer.exceptions.IllegalArgumentDataException;
import cz.muni.fi.airportservicelayer.exceptions.BasicDataAccessException;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabrila Podolnikova
 */
@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightDao flightDao;
    
    @Autowired
    private StewardService stewardService;
    
    @Autowired
    private AirplaneService airplaneService;
    
    //delegation
    @Override
    public void create(Flight f) {
        try {
            flightDao.create(f);
        } catch (Exception e) {
            throw new BasicDataAccessException("Exception on create in FlightDao", e);   
        }
    }

    @Override
    public void delete(Flight f) {
        try {
            flightDao.delete(f);
        } catch (Exception e) {
            throw new BasicDataAccessException("Exception on delete in FlightDao", e);
        }
    }
    
    @Override
    public void update(Flight f) {
        try {
            flightDao.update(f);
        } catch (Exception e) {
            throw new BasicDataAccessException("Exception on update in FlightDao", e);
        }
    }

    @Override
    public Flight findById(Long id) {
        try {
            return flightDao.findById(id);
        } catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on findById in FlightDao", e);
        }
    }

    @Override
    public List<Flight> findAll() {
        try {
            return flightDao.findAll();
        } catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on findAll in FlightDao", e);
        }
    }

    @Override
    public List<Flight> listByDate(boolean arrival) {
        try {
            return flightDao.listByDate(arrival);
        }catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on listByDate in FlightDao", e);
        }
    }

    @Override
    public List<Flight> listByOrigin(Long id) {
        try {
            return flightDao.listByOrigin(id);
        }catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on listByOrigin in FlightDao", e);
        }
    }

    @Override
    public List<Flight> listByDestination(Long id) {
        try {
            return flightDao.listByDestination(id);
        }catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on listByDestination in FlightDao", e);
        }
    }

    @Override
    public boolean verifyAirplane(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("flight is null");
        }
        if (flight.getAirplane() == null) {
            return true;
        }
        try {
            List<Airplane> planes = airplaneService.findSpecificAirplanes(flight.getDeparture(), flight.getArrival(), 0, null);
            return planes.contains(flight.getAirplane());
        }catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on verifyAirplane in FlightDao", e);
        }
    }

    @Override
    public boolean verifyStewards(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("flight is null");
        }
        if (flight.getStewards() == null || flight.getStewards().isEmpty()) {
            return true;
        }
        try {
            List<Steward> stewards = stewardService.findSpecificStewards(flight.getDeparture(), flight.getArrival(), null);
            return subset(flight.getStewards(), stewards);
            
        }catch (Exception e) {
            throw new IllegalArgumentDataException("Exception on verifyStewards in FlightDao", e);
        }
    }
    
    private <T> boolean subset(Collection<T> subSet, Collection<T> supperSet) {
        for (T t : subSet) {
            if(!supperSet.contains(t)) {
                return false;
            }
        }

        return true;
    }
    
}
