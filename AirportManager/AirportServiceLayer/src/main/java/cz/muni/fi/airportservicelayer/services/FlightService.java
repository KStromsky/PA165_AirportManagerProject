/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.entity.Destination;
import cz.muni.fi.airport.entity.Flight;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Gabriela Podolnikova
 */
@Service
public interface FlightService {
    
    /**
     * Creates new entity of Flight
     * 
     * @param f flight to be created
     */
    public void create(Flight f);
    
    /**
     * Deletes the entity of Flight
     * 
     * @param f flight to be deleted
     */
    public void delete(Flight f);
    
    /**
     * Update the entity of Flight
     * 
     * @param f flight to be updated
     */
    public void update(Flight f);
    
    /**
     * Finds the entity of flight by its ID
     * 
     * @param id identificator of flight
     * @return flight with given ID
     */
    public Flight findById(Long id);
    
    /**
     * Lists all persisted flights entities
     * 
     * @return all flight entities
     */
    public List<Flight> findAll();
    
    /**
     * Lists all persisted flights entities sorted by date
     * 
     * @param arrival if true sorted by arrival date otherwise sorted by departure
     * @return all flight entities sorted by date
     */
    public List<Flight> listByDate(boolean arrival);
    
    /**
     * Lists all persisted flights entities departing from the origin
     * @param origin the destination origin that the flight is listed by 
     * @return all flight entities sorted by origin
     */
    public List<Flight> listByOrigin(Long id);
    
    /**
     * Lists all persisted flights entities departing to the destination
     * @param destination the destination that the flight is listed by 
     * @return all flight entities sorted by destination
     */
    public List<Flight> listByDestination(Long id);
    
    /**
     * Verifies, if flight's Airplane is Available at given place at set time period
     * @param flight flight to be checked
     * @return true, if plane is available (or null), else false
     */
    public boolean verifyAirplane(Flight flight);
    
    /**
     * Verifies, if flight's Stewards are Available at given place at set time period
     * @param flight flight to be checked
     * @return true, if stewards are available (or empty), else false
     */
    public boolean verifyStewards(Flight flight);
}
