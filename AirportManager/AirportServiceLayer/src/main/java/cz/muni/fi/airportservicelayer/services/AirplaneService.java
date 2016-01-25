/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportservicelayer.services;

import cz.muni.fi.airport.entity.Airplane;
import cz.muni.fi.airport.entity.Flight;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jakub Stromský
 */

@Service
public interface AirplaneService {
    //DAO delegations
  
    /**
     * Finds airplane by given id
     * 
     * @param id identificator of specific airplane
     * @return airplane which contains given id
     */
    Airplane findById(Long id);
    
    /**
     * Finds all airplanes in system
     * 
     * @return list of all airplanes in system
     */
    List<Airplane> findAllAirplanes();
    
    /**
     * Finds airplanes of given name
     * 
     * @param name name of airplane(s)
     * @return list of airplanes, which contains name string in name
     */
    List<Airplane> findByName(String name);
    
    /**
     * Service for creation of airplane
     * 
     * @param airplane airplane to be created
     */
    void create(Airplane airplane);
    
    /**
     * Service for airplane removal
     * 
     * @param airplane airplane to be removed
     */
    void remove(Airplane airplane);
    
    /**
     * Service for update airplane
     * 
     * @param airplane airplane to be updated
     */
    void update(Airplane airplane);
    
    /**
     * Finds airplanes which names contains given string and their capacities are higher than given capacity.
     * 
     * @param subname part of the name
     * @param type type of airplane
     * @param capacity minimal capacity
     * @return available airplanes in given range of dates
     */
    List<Airplane> findAvailableAirplanes(String subname, String type, int capacity);
    
    /**
     * Finds airplanes which are available in given range of dates.
     * 
     * @param fromDate initial date of range
     * @param toDate end date of range
     * @return available airplanes in given range of dates
     */
    List<Airplane> findAvailableAirplanes(Date fromDate, Date toDate);
    
    /**
     * Finds all flights for given airplane, sort from closest date to latest
     * 
     * @param a airplane for which flights will be found
     * @return list of flights for given airplane
     */
    List<Flight> findAirplaneFlights(Airplane a);    
    //Advanced service
    /**
     * Finds specific airplanes in sense they are available in date interval and
     * they have sufficient capacity
     * 
     * @param fromDate initial date of range
     * @param toDate end date of range
     * @param capacity required capacity of airplane
     * @param location location where airplane have to be
     * @return list of airplanes which suit for given parameters
     */
    List<Airplane> findSpecificAirplanes(Date fromDate, Date toDate, int capacity, String location);
}
