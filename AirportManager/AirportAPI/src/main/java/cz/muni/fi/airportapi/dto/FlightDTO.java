/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Gabriela Podolnikova
 */
public class FlightDTO {

    private Long id;
    private Date arrival;
    private Date departure;
    private DestinationDTO origin;
    private DestinationDTO destination;
    private AirplaneDTO airplane;
    private List<StewardDTO> stewards = new ArrayList<StewardDTO>(); 
    
    /**
     * @return the arrival
     */
    public Date getArrival() {
        return arrival;
    }

    /**
     * @param arrival the arrival to set
     */
    public void setArrival(Date arrival) {
        this.arrival = arrival;
    }

    /**
     * @return the departure
     */
    public Date getDeparture() {
        return departure;
    }

    /**
     * @param departure the departure to set
     */
    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    /**
     * @return the origin
     */
    public DestinationDTO getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(DestinationDTO origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public DestinationDTO getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(DestinationDTO destination) {
        this.destination = destination;
    }

    /**
     * @return the airplane
     */
    public AirplaneDTO getAirplane() {
        return airplane;
    }

    /**
     * @param airplane the airplane to set
     */
    public void setAirplane(AirplaneDTO airplane) {
        this.airplane = airplane;
    }
    
        
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.getArrival());
        hash = 47 * hash + Objects.hashCode(this.getDeparture());
        hash = 47 * hash + Objects.hashCode(this.getOrigin());
        hash = 47 * hash + Objects.hashCode(this.getDestination());
        hash = 47 * hash + Objects.hashCode(this.getAirplane());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FlightDTO)) {
            return false;
        }
        final FlightDTO other = (FlightDTO) obj;
        if (!Objects.equals(this.getArrival(), other.getArrival())) {
            return false;
        }
        if (!Objects.equals(this.getDeparture(), other.getDeparture())) {
            return false;
        }
        if (!Objects.equals(this.getDestination(), other.getDestination())) {
            return false;
        }
        if (!Objects.equals(this.getOrigin(), other.getOrigin())) {
            return false;
        }
        if (!Objects.equals(this.getAirplane(), other.getAirplane())) {
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
    
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the stewards
     */
    public List<StewardDTO> getStewards() {
        return stewards;
    }
    
    public void addSteward(StewardDTO s) {
        stewards.add(s);
    }
    
    public void setStewards(List<StewardDTO> stewards) {
        this.stewards = stewards;
    }
}
