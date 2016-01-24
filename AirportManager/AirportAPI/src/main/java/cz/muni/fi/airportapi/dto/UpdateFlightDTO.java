/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Gabriela Podolnikova
 */
public class UpdateFlightDTO {
    
    private Long id;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date arrival;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date departure;
    
    @NotNull
    @ManyToOne
    private Long originId;
    
    @NotNull
    @ManyToOne
    private Long destinationId;
    
    @NotNull
    @ManyToOne
    private Long airplaneId;
    
    @ManyToMany
    private List<Long> stewardsIds = new ArrayList<>();  

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
     * @return the originId
     */
    public Long getOriginId() {
        return originId;
    }

    /**
     * @param originId the originId to set
     */
    public void setOriginId(Long originId) {
        this.originId = originId;
    }

    /**
     * @return the destinationId
     */
    public Long getDestinationId() {
        return destinationId;
    }

    /**
     * @param destinationId the destinationId to set
     */
    public void setDestinationId(Long destinationId) {
        this.destinationId = destinationId;
    }

    /**
     * @return the airplaneId
     */
    public Long getAirplaneId() {
        return airplaneId;
    }

    /**
     * @param airplaneId the airplaneId to set
     */
    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    /**
     * @return the stewardsIds
     */
    public List<Long> getStewardsIds() {
        return stewardsIds;
    }

    /**
     * @param stewardsIds the stewardsIds to set
     */
    public void setStewardsIds(List<Long> stewardsIds) {
        this.stewardsIds = stewardsIds;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.getArrival());
        hash = 67 * hash + Objects.hashCode(this.getDeparture());
        hash = 67 * hash + Objects.hashCode(this.getOriginId());
        hash = 67 * hash + Objects.hashCode(this.getDestinationId());
        hash = 67 * hash + Objects.hashCode(this.getAirplaneId());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FlightCreationalDTO)) {
            return false;
        }
        final FlightCreationalDTO other = (FlightCreationalDTO) obj;
        if (!Objects.equals(this.getArrival(), other.getArrival())) {
            return false;
        }
        if (!Objects.equals(this.getDeparture(), other.getDeparture())) {
            return false;
        }
        if (!Objects.equals(this.getDestinationId(), other.getDestinationId())) {
            return false;
        }
        if (!Objects.equals(this.getOriginId(), other.getOriginId())) {
            return false;
        }
        if (!Objects.equals(this.getAirplaneId(), other.getAirplaneId())) {
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
}
