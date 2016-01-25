/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gabi
 */
public class UpdateFlightStewardsDTO {
    
    private Long id;
    private List<StewardDTO> stewards = new ArrayList<StewardDTO>();
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.getId());
        hash = 67 * hash + Objects.hashCode(this.getStewards());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UpdateFlightStewardsDTO)) {
            return false;
        }
        final UpdateFlightStewardsDTO other = (UpdateFlightStewardsDTO) obj;
        if (!Objects.equals(this.getStewards(), other.getStewards())) {
            return false;
        }
        return true;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the stewards
     */
    public List<StewardDTO> getStewards() {
        return stewards;
    }

    /**
     * @param stewards the stewards to set
     */
    public void setStewards(List<StewardDTO> stewards) {
        this.stewards = stewards;
    }
}
