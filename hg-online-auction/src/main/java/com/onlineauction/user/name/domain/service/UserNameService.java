package com.onlineauction.user.name.domain.service;

/**
 * Service to register usernames used in the online auction.
 * Note that even users who have been deleted are tracked because we would like to track auctions
 * created by deleted users If a user were allowed to register with a deleted user. All the previous users
 * information would suddenly be valid.
 * 
 * @author hamo
 *
 */
public interface UserNameService {

	/**
	 * Add a user name.
	 * 
	 * @param userName
	 */
	void addUserName(String userName);
	
	/**
	 * Checks if user name is available for registration.
	 * 
	 * @param userName
	 * @return
	 */
	Boolean isUserNameValidForRegistration(String userName);
}
