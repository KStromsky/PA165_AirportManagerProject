/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.dao;

import cz.muni.fi.airport.entity.Steward;
import java.util.List;

/**
 * Interface for Data Access Object for entity Steward
 * 
 * @author Sebastian Kupka
 */
public interface StewardDao {
    
    /**
     * Creates new entity of Steward
     * @param steward steward to be persisted
     */
    public void create(Steward steward);
    
    /**
     * Update the entity of Steward
     * @param steward steward to be updated
     * @return the managed instance of steward that the state was merged to
     */
    public Steward update(Steward steward);
    
    /**
     * Removes the entity of Steward
     * @param steward steward to be deleted
     */
    public void remove(Steward steward);
    
    /**
     * Finds the entity of steward by its ID
     * @param id identificator of steward
     * @return steward with given ID
     */
    public Steward findById(Long id);
    /**
     * Finds the entity of steward by its personal identificator
     * @param identificator personal identificator of steward
     * @return steward with given ID
     */
    public Steward findIdentificator(String identificator);
    
    /**
     * lists all persisted stewards entities
     * @return all stewards entities
     */
    public  List<Steward> findAll();
}
