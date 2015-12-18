package cz.muni.fi.airportapi.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserAuthenticateDTO
{
    private Long userId;
    
    @NotNull
    @Size(min=1, max=50) 
    private String password;
    
    @NotNull
    @Size(min=1, max=50) 
    private String userName;

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }
    
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
}
