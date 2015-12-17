/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.dto.StewardDTO;
import cz.muni.fi.airportapi.dto.UpdateStewardNameDTO;
import cz.muni.fi.airportapi.facade.StewardFacade;
import cz.muni.fi.airportservicelayer.services.BeanMappingService;
import cz.muni.fi.airportservicelayer.services.StewardService;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Sebastian Kupka
 */
@Service
@Transactional
public class StewardFacadeImpl implements StewardFacade {
    
    @Inject
    private StewardService stewardService;
    
    @Inject
    private BeanMappingService beanMappingservice;

    @Override
    public StewardDTO getStewardWithId(Long id) {
        Steward steward = stewardService.findById(id);
        if (steward == null) {
            return null;
        }
        return beanMappingservice.mapTo(steward, StewardDTO.class);
    }

    @Override
    public StewardDTO getStewardWithPersonalIdentificator(String personalIdentificator) {
        Steward steward = stewardService.findByPersonalIdentificator(personalIdentificator);
        if (steward == null) {
            return null;
        }
        return beanMappingservice.mapTo(steward, StewardDTO.class);
    }

    @Override
    public List<StewardDTO> getAllStewards() {
        return beanMappingservice.mapTo(stewardService.findAllStewards(), StewardDTO.class);
    }

    @Override
    public List<StewardDTO> getStewardWithName(String name, String surname) {
        return beanMappingservice.mapTo(stewardService.findByName(name, surname), StewardDTO.class);
    }

    @Override
    public Long createSteward(StewardCreationalDTO steward) {
        Steward createSteward = beanMappingservice.mapTo(steward, Steward.class);
        return stewardService.createSteward(createSteward);
    }

    @Override
    public void removeSteward(Long id) {
        stewardService.removeSteward(id);
    }

    @Override
    public void updateStewardName(UpdateStewardNameDTO update) {
        Steward oldSteward = stewardService.findById(update.getId());
        oldSteward.setFirstname(update.getFirstname());
        oldSteward.setSurname(update.getSurname());
        stewardService.updateSteward(oldSteward);
    }

    @Override
    public List<StewardDTO> getAvailableStewardsAtLocation(Long locationId) {
        return beanMappingservice.mapTo(stewardService.findAvailableStewardsAtLocation(locationId), StewardDTO.class);
    }

    @Override
    public List<FlightDTO> getStewardFlights(Long id) {
        return beanMappingservice.mapTo(stewardService.findStewardFlights(stewardService.findById(id)), FlightDTO.class);
    }
    
    @Override
    public List<StewardDTO> findSpecificStewards(Date fromDate, Date toDate, Long locationId) {
        return beanMappingservice.mapTo(stewardService.findSpecificStewards(fromDate, toDate, locationId), StewardDTO.class);
    }
    
}
