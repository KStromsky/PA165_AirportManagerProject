package cz.muni.fi.airportapi.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.swing.text.View;

public class UserDTO
{
    private Long id;
    
    private String passwordHash;
    
    private String userName;

    public UserDTO(){
        
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   // @JsonIgnore
    public String getPasswordHash() {
        return passwordHash;
    }

  //  @JsonProperty
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userName == null) ? 0 : userName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserDTO other = (UserDTO) obj;
        if (userName == null) {
            if (other.userName != null)
                return false;
        } else if (!userName.equals(other.userName))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", passwordHash='" + passwordHash + '\'' +
                ", userName='" + userName + 
                '}';
    }
}
