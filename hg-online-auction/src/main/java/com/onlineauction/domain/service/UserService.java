package com.onlineauction.domain.service;

import com.onlineauction.domain.entity.User;

/**
 * User Service.
 * 
 * @author hamo26
 *
 */
public interface UserService {
	
	/**
	 * Get a user given a user id.
	 * 
	 * @param userId the userId
	 * @return a user
	 */
	User getUserByUserId(String userId); 
	
	/**
	 * Get a user by username and password.
	 * 
	 * @param userName the user name
	 * @param password the password
	 * @return the user
	 */
	User getUserByUserNameAndPassword(String userName, String password);
	
	/**
	 * Subscribe a user to online auction
	 * 
	 * @param user the user
	 */
	void subscribeUser(User user);
	
	/**
	 * Deletes a user from the system
	 * 
	 * @param userId the user id
	 */
	void deleteUser(String userId);
}
