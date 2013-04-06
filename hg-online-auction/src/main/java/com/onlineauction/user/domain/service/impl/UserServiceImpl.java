package com.onlineauction.user.domain.service.impl;

import java.util.ArrayList;
import java.util.HashSet;

import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
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
	public User getUserByUserName(String userName) throws HgException{
		User user = HgDataService.objectify()
					 .load()
					 .type(User.class)
					 .filter("userName ==", userName)
					 .first().get();
		if (user == null) {
			//throw an exception stating that the user was not found.
			throw new HgException("User with username: " + userName + " does not exist");
		} 
		
		return user;
	}

	@Override
	public User getUserByUserNameAndPassword(String userName, String password) throws HgException{
		User user = HgDataService.objectify()
					 .load()
					 .type(User.class)
					 .filter("userName ==", userName)
					 .filter("password ==", password)
					 .first()
					 .get();
		
		if (user == null) {
			//throw an exception stating that the user name or password was incorrect.
			throw new HgException("Username or password was incorrect");
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
	public void deleteUser(String userId) throws HgException{
		User userByUserId = getUserByUserName(userId);
		
		HgDataService.objectify()
					 .delete()
					 .entity(userByUserId);
	}

	@Override
	public void rateUser(String userId, Rating rating) throws HgException {
		User user = getUserByUserName(userId);
		
		if (user.getRatings() == null) {
			user.setRatings(new ArrayList<Rating>());
		}
		
		user.getRatings().add(rating);
		
		HgDataService.objectify()
					 .save()
					 .entity(user);
	}

	@Override
	public void addUserBid(String userId, Bid bid) throws HgException{
		User user = getUserByUserName(userId);
		
		if (user.getBids() == null) {
			user.setBids(new ArrayList<Bid>());
			user.setAuctionsIds(new HashSet<Long>());
		}
		
		user.getBids().add(bid);
		user.getAuctionsIds().add(bid.getAuctionId());
		
		HgDataService.objectify()
					 .save()
					 .entity(user);
	}
}
