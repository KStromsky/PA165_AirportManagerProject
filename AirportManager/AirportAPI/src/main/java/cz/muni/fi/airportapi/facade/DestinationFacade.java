/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.facade;

import cz.muni.fi.airportapi.dto.DestinationCreationalDTO;
import cz.muni.fi.airportapi.dto.DestinationDTO;
import cz.muni.fi.airportapi.dto.UpdateDestinationLocationDTO;
import java.util.List;

/**
 *
 * @author Michal Zbranek
 */
public interface DestinationFacade {
    
    /**
     * Gets Destination DTO with given id.
     * 
     * @param id identificator of destination
     * @return 
     */
    public DestinationDTO getDestinationWithId(Long id);
    
    /**
     * Gets Update Destination Location DTO with given id.
     * 
     * @param id identificator of destination
     * @return 
     */
    public UpdateDestinationLocationDTO getUpdateDestinationLocationWithId(Long id);
    
    /**
     * Gets Destination DTO with location
     * 
     * @param location location of destination
     * @return 
     */
    public List<DestinationDTO> getDestinationWithLocation(String location);
    
    /**
     * Lists all Destination DTOs
     * 
     * @return 
     */
    public List<DestinationDTO> getAllDestinations();
    
    /**
     * Creates new Destination DTO
     * 
     * @param destination 
     * @return  
     */
    public Long createDestination(DestinationCreationalDTO destination);
    
    /**
     * Removes Destination DTO
     * 
     * @param id identificator of destination
     */
    public void removeDestination(Long id);
    
    /**
     * Updates Destination DTO location
     * 
     * @param update update with new location
     */
    public void updateDestinationLocation(UpdateDestinationLocationDTO update);
}

