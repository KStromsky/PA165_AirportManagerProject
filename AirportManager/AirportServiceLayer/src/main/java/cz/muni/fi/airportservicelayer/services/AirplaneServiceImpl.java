/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.dao.AirplaneDao;
import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airportservicelayer.exceptions.BasicDataAccessException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jakub Stromský
 */
@Service
public class AirplaneServiceImpl implements AirplaneService {

    @Autowired
    private AirplaneDao airplaneDao;

    //DAO delegations
    @Override
    public Airplane findById(Long id) {
        try {
            return airplaneDao.findById(id);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findById in AirplaneDao", ex);
        }

    }

    @Override
    public List<Airplane> findAllAirplanes() {
        try {
            return airplaneDao.findAllAirplanes();
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findAllAirplanes"
                    + " in Airplane Dao", ex);
        }

    }

    @Override
    public List<Airplane> findByName(String name) {
        try {
            return airplaneDao.findByName(name);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findByName in AirplaneDao", ex);
        }

    }

    @Override
    public void create(Airplane airplane) {
        try {
            airplaneDao.create(airplane);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on create in AirplaneDao", ex);
        }
    }

    @Override
    public void remove(Airplane airplane) {
        try {
            airplaneDao.delete(airplane);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on delete in AirplaneDao", ex);
        }
    }

    @Override
    public void update(Airplane airplane) {
        try {
            airplaneDao.update(airplane);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on update in AirplaneDao", ex);
        }
    }

    @Override
    public List<Airplane> findAvailableAirplanes(String subname, String type, int capacity) {
        try {
            if (type == null) {
                type = new String();
            } else {
                type = type.toLowerCase();
            }
            List<Airplane> all;
            if (subname == null) {
                all = airplaneDao.findAllAirplanes();
            } else {
                all = airplaneDao.findByName(subname);
            }

            List<Airplane> filtered = new ArrayList<Airplane>();
            for (Airplane plane : all) {
                if (plane.getCapacity() >= capacity && plane.getType().toLowerCase().contains(type)) {
                    filtered.add(plane);
                }
            }
            return filtered;
            
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on update in AirplaneDao", ex);
        }
    }

    @Override
    public List<Airplane> findAvailableAirplanes(Date fromDate, Date toDate) {
        try {
            return airplaneDao.findAvailableAirplanes(fromDate, toDate);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findAvailableAirplanes in AirplaneDao", ex);
        }
    }

    @Override
    public List<Flight> findAirplaneFlights(Airplane a) {
        try {
            return airplaneDao.findAirplaneFlights(a);
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findAirplaneFlights in AirplaneDao", ex);
        }
    }

    //Advanced service
    @Override
    public List<Airplane> findSpecificAirplanes(Date fromDate, Date toDate, int capacity, String location) {
        try {
            List<Airplane> availableAirplanes;
            if (fromDate != null && toDate != null) {
                availableAirplanes = airplaneDao.findAvailableAirplanes(fromDate, toDate);
            } else {
                availableAirplanes = airplaneDao.findAllAirplanes();
            }
            List<Airplane> specificAirplanes = new ArrayList<>();
            for (Airplane airplane : availableAirplanes) {
                if (airplane.getCapacity() >= capacity
                        && (location == null
                        || (!airplaneDao.findAirplaneFlights(airplane).isEmpty()
                        && location.equals(airplaneDao.findAirplaneFlights(airplane).get(0).getDestination().getLocation())))) {
                    specificAirplanes.add(airplane);
                }
            }
            return specificAirplanes;
        } catch (Exception ex) {
            throw new BasicDataAccessException("Exception on findSpecificAirplanes in AirplaneDao", ex);
        }
    }
}
