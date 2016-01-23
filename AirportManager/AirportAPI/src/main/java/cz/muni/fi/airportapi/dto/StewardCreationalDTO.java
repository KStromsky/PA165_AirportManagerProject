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
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Sebastian Kupka
 */
public class StewardCreationalDTO {
    
    @NotNull
    @Pattern(regexp="[0-9,A-Z]{3}-\\d{5}", message = "Valid personal info has to be filled in. \n Example: A1C-12345")
    private String personalIdentificator;
    @NotNull
    @Size(min=1, max=50)
    private String firstname;
    @NotNull
    @Size(min=1, max=50) 
    private String surname;
    @NotNull
    private Gender gender;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dateOfBirth;
    @NotNull
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date employmentDate;
    
    @NotNull
    @Size(min=1, max=50) 
    private String username;
    
    @NotNull
    @Size(min = 5, max = 20)
    private String password;
    

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
        if (!(obj instanceof StewardCreationalDTO)) {
            return false;
        }
        final StewardCreationalDTO other = (StewardCreationalDTO) obj;
        if (!Objects.equals(this.getPersonalIdentificator(), other.getPersonalIdentificator())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StewardCreationalDTO{" + "personalIdentificator=" + personalIdentificator 
                + ", firstname=" + firstname 
                + ", surname=" + surname 
                + ", gender=" + gender 
                + ", dateOfBirth=" + dateOfBirth 
                + ", employmentDate=" + employmentDate 
                + ", username=" + username + '}';
    }

    
}
