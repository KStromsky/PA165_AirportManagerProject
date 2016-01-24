/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Class representing the entity of Flight
 * 
 * @author Gabriela Podolnikova
 */
@Entity
public class Flight {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
   
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date arrival;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date departure;
    
    @NotNull
    @ManyToOne
    private Destination origin;
    
    @NotNull
    @ManyToOne
    private Destination destination;
    
    @NotNull
    @ManyToOne
    private Airplane airplane;
    

    @ManyToMany
    private List<Steward> stewards = new ArrayList<Steward>(); 
    
    public Flight() {       
    }
    
    public Flight(Long id) {
        this.id = id;
    }

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
    public Destination getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(Destination origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public Destination getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    /**
     * @return the airplane
     */
    public Airplane getAirplane() {
        return airplane;
    }

    /**
     * @param airplane the airplane to set
     */
    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }
    
        
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.getArrival());
        hash = 67 * hash + Objects.hashCode(this.getDeparture());
        hash = 67 * hash + Objects.hashCode(this.getOrigin());
        hash = 67 * hash + Objects.hashCode(this.getDestination());
        hash = 67 * hash + Objects.hashCode(this.getAirplane());
        return hash;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Flight)) {
            return false;
        }
        final Flight other = (Flight) obj;
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

    /**
     * @return the stewards
     */
    public List<Steward> getStewards() {
        return stewards;
    }
    
    public void addSteward(Steward s) {
        getStewards().add(s);
    }
    
    public void removeSteward(Steward s) {
        getStewards().remove(s);
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @param stewards the stewards to set
     */
    public void setStewards(List<Steward> stewards) {
        this.stewards = stewards;
    }
}
