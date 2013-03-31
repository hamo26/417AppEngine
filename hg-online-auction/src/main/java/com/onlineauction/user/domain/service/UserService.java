package com.onlineauction.user.domain.service;

import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;

/**
 * User Service.
 * 
 * @author hamo26
 *
 */
public interface UserService {
	
	/**
	 * Get a user given a user name.
	 * 
	 * @param userName the user name
	 * @return a user
	 */
	User getUserByUserName(String userName); 
	
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
	
	/**
	 * Associate a rating with a user.
	 * 
	 * @param userId
	 * @param rating
	 */
	void rateUser(String userId, Rating rating);
}
