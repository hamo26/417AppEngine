package com.onlineauction.user.domain.service.impl;

import com.onlineauction.objectify.HgDataService;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

/**
 * Implementation of {@link UserService}.
 * 
 * @author hamo
 *
 */
public class UserServiceImpl implements UserService {

	@Override
	public User getUserByUserName(String userName) {
		User user = HgDataService.objectify()
					 .load()
					 .type(User.class)
					 .filter("userName ==", userName)
					 .first().get();
		if (user == null) {
			//throw an exception stating that the user was not found.
			
		} 
		
		return user;
	}

	@Override
	public User getUserByUserNameAndPassword(String userName, String password) {
		User user = HgDataService.objectify()
					 .load()
					 .type(User.class)
					 .filter("userName ==", userName)
					 .filter("password ==", password)
					 .first()
					 .get();
		
		if (user == null) {
			//throw an exception stating that the user name or password was incorrect.
		}
		
		return user;
	}

	@Override
	public void subscribeUser(User user) {
		HgDataService.objectify()
					 .save()
					 .entity(user);
	}

	@Override
	public void deleteUser(String userId) {
		User userByUserId = getUserByUserName(userId);
		
		HgDataService.objectify()
					 .delete()
					 .entity(userByUserId);
	}

	@Override
	public void rateUser(String userId, Rating rating) {
		User user = getUserByUserName(userId);
		
		user.getRatings().add(rating);
		
		HgDataService.objectify()
					 .save()
					 .entity(user);
	}
}
