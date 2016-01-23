/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airportapi.dto;

import cz.muni.fi.airport.entity.Steward;
import cz.muni.fi.airport.enums.Gender;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Sebastian Kupka
 */
public class StewardDTO {
    private Long id;
    private String personalIdentificator;
    private String firstname;
    private String surname;
    private Gender gender;
    private Date dateOfBirth;
    private Date employmentDate;
    private String username;
    private boolean isAdmin;

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
    public String getPersonalIdentificator() {
        return personalIdentificator;
    }

    /**
     * Sets the personal identificator of steward
     * @param personalIdentificator personal identificator
     */
    public void setPersonalIdentificator(String personalIdentificator) {
        this.personalIdentificator = personalIdentificator;
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

    /**
     * Gets the gender of steward
     * @return gender 
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Sets the gender of steward
     * @param gender gender
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Gets the date of birth of steward
     * @return date of birth
     */
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    /**
     * Sets the date of birth of steward
     * @param dateOfBirth date of birth
     */
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets the date of employment agreement of steward
     * @return date of employment agreement
     */
    public Date getEmploymentDate() {
        return employmentDate;
    }

    /**
     * Sets the date of employment agreement of steward
     * @param employmentDate date of employment agreement
     */
    public void setEmploymentDate(Date employmentDate) {
        this.employmentDate = employmentDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.personalIdentificator);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StewardDTO)) {
            return false;
        }
        final StewardDTO other = (StewardDTO) obj;
        if (!Objects.equals(this.getPersonalIdentificator(), other.getPersonalIdentificator())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StewardDTO{" + "id=" + id 
                + ", personalIdentificator=" + personalIdentificator 
                + ", firstname=" + firstname 
                + ", surname=" + surname 
                + ", gender=" + gender 
                + ", dateOfBirth=" + dateOfBirth 
                + ", employmentDate=" + employmentDate 
                + ", username=" + username 
                + ", isAdmin=" + isAdmin + '}';
    }

    
}
