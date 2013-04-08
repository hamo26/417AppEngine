package com.onlineauction.user.domain.service;

import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;

/**
 * Service to creating, updating and deleting (CRUD) users as well as other useful methods.
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
	User getUserByUserName(String userName) throws HgException; 
	
	/**
	 * Get a user by username and password.
	 * 
	 * @param userName the user name
	 * @param password the password
	 * @return the user
	 */
	User getUserByUserNameAndPassword(String userName, String password) throws HgException;
	
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
	void deleteUser(String userId) throws HgException;
	
	/**
	 * Associate a rating with a user.
	 * 
	 * @param userId
	 * @param rating
	 */
	void rateUser(String userId, Rating rating) throws HgException;
	
	/**
	 * Add a bid a user has created.
	 * 
	 * @param userId
	 * @param bid
	 */
	void addUserBid(String userId, Bid bid) throws HgException;
}
