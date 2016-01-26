/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateDestinationLocationDTO;
import cz.muni.fi.airportapi.facade.DestinationFacade;
import cz.muni.fi.airportservicelayer.services.BeanMappingService;
import cz.muni.fi.airportservicelayer.services.DestinationService;
import java.util.List;
import javax.inject.Inject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Michal Zbranek
 */
@Service
@Transactional
public class DestinationFacadeImpl implements DestinationFacade{
    
    @Inject
    private DestinationService destinationService;
    
    @Inject
    private BeanMappingService beanMappingservice;

    @Override
    public DestinationDTO getDestinationWithId(Long id) {
        Destination destination = destinationService.findById(id);
        if (destination == null) {
            return null;
        }
        return beanMappingservice.mapTo(destination, DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getDestinationWithLocation(String location) {
        return beanMappingservice.mapTo(destinationService.findByLocation(location), DestinationDTO.class);
    }

    @Override
    public List<DestinationDTO> getAllDestinations() {
        return beanMappingservice.mapTo(destinationService.findAllDestinations(), DestinationDTO.class);
    }

    @Override
    public Long createDestination(DestinationCreationalDTO destination) {
        Destination createDestination = beanMappingservice.mapTo(destination, Destination.class);
        destinationService.create(createDestination);
        return createDestination.getId();
    }

    @Override
    public void removeDestination(Long id) {
        destinationService.remove(destinationService.findById(id));
    }

    @Override
    public void updateDestinationLocation(UpdateDestinationLocationDTO update) {
        Destination oldDestination = destinationService.findById(update.getId());
        oldDestination.setLocation(update.getLocation());
        destinationService.update(oldDestination);
    }

    @Override
    public UpdateDestinationLocationDTO getUpdateDestinationLocationWithId(Long id) {
        Destination destination = destinationService.findById(id);
        if (destination == null) {
            return null;
        }
        return beanMappingservice.mapTo(destination, UpdateDestinationLocationDTO.class);
    }
    
}
