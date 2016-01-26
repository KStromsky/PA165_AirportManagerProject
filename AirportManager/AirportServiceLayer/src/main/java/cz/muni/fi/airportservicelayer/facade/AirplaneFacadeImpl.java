/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airportapi.dto.AirplaneCreationalDTO;
import cz.muni.fi.airportapi.dto.AirplaneDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.UpdateAirplaneCapacityDTO;
import cz.muni.fi.airportapi.facade.AirplaneFacade;
import cz.muni.fi.airportservicelayer.services.AirplaneService;
import cz.muni.fi.airportservicelayer.services.BeanMappingService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jakub Stromský
 */

@Service
@Transactional
public class AirplaneFacadeImpl implements AirplaneFacade {
    
    @Autowired
    private AirplaneService airplaneService;
    
    @Autowired
    private BeanMappingService beanMappingservice;

    @Override
    public AirplaneDTO getAirplaneWithId(Long id) {
        return beanMappingservice.mapTo(airplaneService.findById(id), AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> getAllAirplanes() {
        return beanMappingservice.mapTo(airplaneService.findAllAirplanes(), AirplaneDTO.class);
    }

    @Override
    public List<AirplaneDTO> getAirplaneWithName(String name) {
        return beanMappingservice.mapTo(airplaneService.findByName(name), AirplaneDTO.class);
    }

    @Override
    public Long createAirplane(AirplaneCreationalDTO a) {
        Airplane mappedAirplane = beanMappingservice.mapTo(a, Airplane.class);
        airplaneService.create(mappedAirplane);
        return mappedAirplane.getId();
    }

    @Override
    public void removeAirplane(Long id) {
        airplaneService.remove(airplaneService.findById(id));
    }

    @Override
    public void updateAirplaneCapacity(UpdateAirplaneCapacityDTO update) {
        Airplane a = airplaneService.findById(update.getId());
        a.setCapacity(update.getCapacity());
        airplaneService.update(a);
    }

    @Override
    public List<AirplaneDTO> getAvailableAirplanes(Date from, Date to) {
        return beanMappingservice.mapTo(airplaneService.findAvailableAirplanes(from, to), AirplaneDTO.class);
    }
    
    @Override
    public List<AirplaneDTO> getAvailableAirplanes(String subname, String type, int capacity) {
        return beanMappingservice.mapTo(airplaneService.findAvailableAirplanes(subname, type, capacity), AirplaneDTO.class);
    }

    @Override
    public List<FlightDTO> getAirplaneFlights(Long id) {
        return beanMappingservice.mapTo(airplaneService.findAirplaneFlights(airplaneService.findById(id))
                , FlightDTO.class);
    }

    @Override
    public List<AirplaneDTO> getSpecificAirplanes(Date from, Date to, int capacity, String location) {
        return beanMappingservice.mapTo(airplaneService.findSpecificAirplanes(from, to, capacity, location)
                , AirplaneDTO.class);
    }

    @Override
    public UpdateAirplaneCapacityDTO getUpdateAirplaneCapacityWithId(Long id) {
        return beanMappingservice.mapTo(airplaneService.findById(id), UpdateAirplaneCapacityDTO.class);
    }
       
}
