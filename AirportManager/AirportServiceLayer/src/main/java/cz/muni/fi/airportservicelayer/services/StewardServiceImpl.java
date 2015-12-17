/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.dao.DestinationDao;
import cz.muni.fi.airport.dao.StewardDao;
import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airportservicelayer.exceptions.IllegalArgumentDataException;
import cz.muni.fi.airportservicelayer.exceptions.BasicDataAccessException;
import cz.muni.fi.airportservicelayer.exceptions.ValidationDataException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import javax.validation.ValidationException;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Sebastian Kupka
 */
@Service
public class StewardServiceImpl implements StewardService {
    
    @Inject
    private StewardDao stewardDao;

    @Override
    public Steward findById(Long id) {
        if (id == null) {
            return null;
        }
        try {
            return stewardDao.findById(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public Steward findByPersonalIdentificator(String personalIdentificator) {
        if (personalIdentificator == null) {
            return null;
        }
        try {
            return stewardDao.findByIdentificator(personalIdentificator);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> findAllStewards() {
        try {
            return stewardDao.findAll();
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public List<Steward> findByName(String name, String surname) {
        if (name == null) {
            throw new IllegalArgumentException("name");
        }
        if (surname == null) {
            throw new IllegalArgumentException("surname");
        }
        try {
            return stewardDao.findByName(name, surname);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public Long createSteward(Steward steward) {
        if (steward == null) {
            return null;
        }
        try {
            stewardDao.create(steward);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
        return steward.getId();
    }

    @Override
    public void removeSteward(Long id) {
        if (id == null) {
            return;
        }
        try {
            stewardDao.remove(id);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    @Override
    public void updateSteward(Steward update) {
        if (update == null ||update.getId() == null) {
            return;
        }
        try {
            stewardDao.update(update);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }
    
    @Override
    public List<Flight> findStewardFlights(Steward steward) {
        if (steward == null || steward.getId() == null) {
            return new ArrayList<Flight>();
        }
        try {
            return stewardDao.findLastStewardFlights(steward);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }
    
    @Override
    public List<Steward> findAvailableStewards(Date fromDate, Date toDate) {
        if (fromDate == null) {
            throw new IllegalArgumentException("fromDate");
        }
        if (toDate == null) {
            throw new IllegalArgumentException("toDate");
        }
        if (fromDate.compareTo(toDate) > 0) {
            throw new IllegalArgumentException("fromDate is later than toDate");
        }
        try {
            return stewardDao.findAvailableStewards(fromDate, toDate);
        } catch (IllegalArgumentException | NullPointerException ex) {
            throw new IllegalArgumentDataException(ex);
        } catch (ValidationException ex) {
            throw new ValidationDataException(ex);
        } catch (Exception ex) {
            throw new BasicDataAccessException(ex);
        }
    }

    //Advanced service
    @Override
    public List<Steward> findSpecificStewards(Date fromDate, Date toDate, Long locationId) {
        List<Steward> availableStewards = null;
        if (fromDate != null || toDate != null) {
            if (fromDate == null) {
                fromDate = toDate;
            }
            
            if (toDate == null) {
                toDate = fromDate;
            }
            availableStewards = this.findAvailableStewards(fromDate, toDate);
        } else {
            availableStewards = this.findAllStewards();
        }
        if (locationId != null) {
            return findSpecificStewards(availableStewards, locationId);
        } else {
            return availableStewards;
        }
    }
    
    @Override
    public List<Steward> findAvailableStewardsAtLocation(long locationId) {
        return this.findSpecificStewards(null, null, locationId);
    }

    private List<Steward> findSpecificStewards(List<Steward> availableStewards, long locationId) {
        List<Steward> specificAirplanes = new ArrayList<>();
        for(Steward steward : availableStewards) {
            Destination dest = this.findStewardLocation(steward);
            if (dest != null && new Long(locationId).equals(dest.getId())) {
                specificAirplanes.add(steward);
            }
        }
        return specificAirplanes;
    }

    @Override
    public Destination findStewardLocation(Steward steward) {
        List<Flight> flights = this.findStewardFlights(steward);
        if (flights == null || flights.isEmpty()) {
            return null;
        } else {
            return flights.get(0).getDestination();
        }
    }
}
