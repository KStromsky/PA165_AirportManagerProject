/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.entity;

import cz.muni.airport.enums.Gender;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class representing the entity of steward
 * 
 * @author Sebastian Kupka
 */
@Entity
public class Steward {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable=false,unique=true)
    @Pattern(regexp="\\d{3}-\\d{5}")
    private String personalIdentificator;
    
    @NotNull
    private String firstname;
    
    @NotNull
    private String surname;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    
    @NotNull
    @Temporal(TemporalType.DATE)
    private Date employmentDate;

    /**
     * Gets the entity Database ID
     * @return personal identificator
     */
    public Long getId() {
        return id;
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
        if (!(obj instanceof Steward)) {
            return false;
        }
        final Steward other = (Steward) obj;
        if (!Objects.equals(this.personalIdentificator, other.getPersonalIdentificator())) {
            return false;
        }
        return true;
    }
    
    
    
}
