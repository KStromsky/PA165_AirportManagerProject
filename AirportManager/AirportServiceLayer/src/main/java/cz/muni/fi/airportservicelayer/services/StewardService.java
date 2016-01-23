/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import cz.muni.fi.airport.entity.Steward;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Sebastian Kupka
 */
public interface StewardService {
    /**
     * Gets Steward DTO with given id.
     * @param id identificator
     * @return 
     */
    public Steward findById(Long id);
    
    /**
     * Gets Steward DTO with personal identificator.
     * @param personalIdentificator personal identificator
     * @return 
     */
    public Steward findByPersonalIdentificator(String personalIdentificator);
    
    /**
     * Lists all Stewards.
     * @return 
     */
    public List<Steward> findAllStewards();
    
    /**
     * Lists all Stewards with given name.
     * @param name first name
     * @param surname surname
     * @return 
     */
    public List<Steward> findByName(String name, String surname);
    
    /**
     * Creates new Steward
     * @param steward 
     * @param password not hashed pw
     * @return stewards id
     */
    public Long createSteward(Steward steward, String password);
    
    /**
     * Removes Steward
     * @param id identificator
     */
    public void removeSteward(Long id);
    
    /**
     * Updates Steward
     * @param update Update with new name
     */
    public void updateSteward(Steward update);
    
    /**
     * Finds stewards, that are not assigned to a flight during given period
     * @param fromDate available from
     * @param toDate available to
     * @return 
     */
    public List<Steward> findAvailableStewards(Date fromDate, Date toDate);
    
    /**
     * Find Flight history for given steward
     * @param steward
     * @return 
     */
    public List<Flight> findStewardFlights(Steward steward);
    
    /**
     * Returns the place, where the steward is currently in.
     * @param steward
     * @return 
     */
    public Destination findStewardLocation(Steward steward);

    //Advanced service
    
    /**
     * Lists all Stewards at given location during given period.
     * @param fromDate available from
     * @param toDate available to
     * @param locationId identificator of the destination.
     * @return 
     */
    public List<Steward> findSpecificStewards(Date fromDate, Date toDate, Long locationId);
    
    /**
     * Lists all Stewards at given location.
     * @param locationId identificator of the destination.
     * @return 
     */
    public List<Steward> findAvailableStewardsAtLocation(long locationId);
    
    public Steward findByUsername(String username);
    
    public boolean authentication(Steward steward, String pw);
}
