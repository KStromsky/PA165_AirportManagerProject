package cz.muni.fi.airportapi.dto;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import javax.swing.text.View;

public class UserDTO
{
    protected Long id;
    protected String username;
    protected boolean admin;

    public UserDTO(){
        
    }
    
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

   public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
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
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", admin='" + admin + '\'' +
                ", username='" + username + 
                '}';
    }
}
