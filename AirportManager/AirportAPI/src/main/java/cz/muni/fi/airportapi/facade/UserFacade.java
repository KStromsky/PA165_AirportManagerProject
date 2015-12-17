package cz.muni.fi.airportapi.facade;

import cz.muni.fi.airportapi.dto.UserAuthenticateDTO;
import cz.muni.fi.airportapi.dto.UserDTO;
import java.util.Collection;

public interface UserFacade {
	
	UserDTO findUserById(Long userId);

	UserDTO findUserByUserName(String email);
	
	/**
	 * Register the given user with the given unencrypted password.
	 */
	void registerUser(UserDTO u, String unencryptedPassword);

	/**
	 * Get all registered users
	 */
	Collection<UserDTO> getAllUsers();

	/**
	 * Try to authenticate a user. Return true only if the hashed password matches the records.
	 */
	boolean authenticate(UserAuthenticateDTO u);

	/**
	 * Check if the given user is admin.
	 */
	boolean isAdmin(UserDTO u);

}
