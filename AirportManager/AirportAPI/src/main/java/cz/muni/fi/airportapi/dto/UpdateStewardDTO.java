/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.dto;

import cz.muni.fi.airport.enums.Gender;
import java.util.Date;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Sebastian Kupka
 */
public class UpdateStewardDTO {
    private Long id;
    @NotNull
    @Size(min=1, max=50)
    private String firstname;
    @NotNull
    @Size(min=1, max=50) 
    private String surname;
    
    @Size(min=5, max=20) 
    private String password;
    
    /**
     * Gets the entity Database ID
     * @return identificator
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the entity Database ID
     * @param id identificator
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Gets the personal identificator of steward
     * @return personal identificator
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname of steward
     * @param firstname firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Gets the surname of steward
     * @return surname 
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of steward
     * @param surname surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UpdateStewardDTO)) {
            return false;
        }
        final UpdateStewardDTO other = (UpdateStewardDTO) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UpdateStewardNameDTO{" + 
                "id=" + id + 
                ", firstname=" + firstname + 
                ", surname=" + surname + 
                ", password=" + password + 
                '}';
    }
}
