/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.facade;

import cz.muni.fi.airportapi.dto.StewardCreationalDTO;
import cz.muni.fi.airportapi.dto.StewardDTO;
import cz.muni.fi.airportapi.dto.FlightDTO;
import cz.muni.fi.airportapi.dto.StewardAuthDTO;
import cz.muni.fi.airportapi.dto.UpdateStewardDTO;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sebastian Kupka
 */
public interface StewardFacade {
    /**
     * Gets Steward DTO with given id.
     * @param id identificator
     * @return 
     */
    public StewardDTO getStewardWithId(Long id);
    
    /**
     * Gets Steward Update DTO with given id.
     * @param id identificator
     * @return 
     */
    public UpdateStewardDTO getUpdateStewardWithId(Long id);
    
    /**
     * Gets Steward DTO with personal identificator.
     * @param personalIdentificator personal identificator
     * @return 
     */
    public StewardDTO getStewardWithPersonalIdentificator(String personalIdentificator);
    
    /**
     * Gets Stewards DTO with given partail name and personal identificator.
     * @param personalIdentificator part of personal identificator
     * @param name part of first name
     * @param surname part of second name
     * @return 
     */
    public List<StewardDTO> getRelevantStewards(String personalIdentificator, String name, String surname);
    
    /**
     * Lists all Steward DTOs
     * @return 
     */
    public List<StewardDTO> getAllStewards();
    
    /**
     * Lists all Steward DTOs with given name.
     * @param name first name
     * @param surname surname
     * @return 
     */
    public List<StewardDTO> getStewardWithName(String name, String surname);
    
    /**
     * Creates new Steward DTO
     * @param steward 
     */
    public Long createSteward(StewardCreationalDTO steward);
    
    /**
     * Removes Steward DTO
     * @param id identificator
     */
    public void removeSteward(Long id);
    
    /**
     * Updates Steward DTO Name
     * @param update Update with new name
     */
    public void updateStewardName(UpdateStewardDTO update);
    
    /**
     * Gets all Stewards' DTO at given location.
     * @param locationId identificator of the destination.
     * @return 
     */
    public List<StewardDTO> getAvailableStewardsAtLocation(Long locationId);
    
    /**
     * Lists all flights for given Steward
     * @param id Steward's identificator
     * @return 
     */
    public List<FlightDTO> getStewardFlights(Long id);
    
    /**
     * Lists all Stewards' DTO at given location during given period.
     * @param fromDate available from
     * @param toDate available to
     * @param locationId identificator of the destination.
     * @return 
     */
    public List<StewardDTO> findSpecificStewards(Date fromDate, Date toDate, Long locationId);
    
    public StewardDTO getStewardWithUsername(String username);
    
    public boolean authentication(StewardAuthDTO authDTO);
}
