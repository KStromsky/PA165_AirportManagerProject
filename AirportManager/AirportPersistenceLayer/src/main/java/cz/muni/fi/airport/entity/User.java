/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.airport.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 *
 * @author Sebastian Kupka
 */
@Entity
//In Derby, its forbiden to 'USER' is reserved keyword, we need to rename table 
@Table(name = "Users")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    protected Long id;
    
    // Setter pre testy
    public void setId(Long id) {
        this.id = id;
    } 
    
    /**
     * Gets the entity Database ID
     * @return personal identificator
     */
    public Long getId() {
        return id;
    }
    
    @NotNull
    protected boolean admin;
    
    @NotNull
    @Column(nullable = false, unique = true)
    protected String username;
    
    protected String pwHash;
    
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwHash() {
        return pwHash;
    }

    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getUsername() == null) ? 0 : this.getUsername().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User other = (User) obj;
        if (this.getUsername() == null) {
            if (other.getUsername() != null) {
                return false;
            }
        } else if (!this.getUsername().equals(other.getUsername())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{"
                + "pwHash='" + pwHash + '\''
                + ", username='" + username + '\''
                + ", admin=" + admin
                + '}';
    }
}