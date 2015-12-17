package cz.muni.fi.airportservicelayer.facade;

import cz.muni.fi.airport.entity.User;
import cz.muni.fi.airportapi.dto.UserAuthenticateDTO;
import cz.muni.fi.airportapi.dto.UserDTO;
import cz.muni.fi.airportapi.facade.UserFacade;
import cz.muni.fi.airportservicelayer.services.BeanMappingService;
import cz.muni.fi.airportservicelayer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@Transactional
public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Autowired
    private BeanMappingService beanMappingService;

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userService.findUserById(userId);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public UserDTO findUserByUserName(String userName) {
        User user = userService.findUserByUserName(userName);
        return (user == null) ? null : beanMappingService.mapTo(user, UserDTO.class);
    }

    @Override
    public void registerUser(UserDTO userDTO, String unencryptedPassword) {
        User userEntity = beanMappingService.mapTo(userDTO, User.class);
        userService.registerUser(userEntity, unencryptedPassword);
        userDTO.setId(userEntity.getId());
    }

    @Override
    public Collection<UserDTO> getAllUsers() {
        return beanMappingService.mapTo(userService.getAllUsers(), UserDTO.class);
    }

    @Override
    public boolean authenticate(UserAuthenticateDTO u) {
        return userService.authenticate(
                userService.findUserById(u.getUserId()), u.getPassword());
    }

    @Override
    public boolean isAdmin(UserDTO u) {
        return userService.isAdmin(beanMappingService.mapTo(u, User.class));
    }

}
