package com.onlineauction.user.domain.entity;

import java.util.ArrayList;
import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.rating.domain.entity.Rating;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of="userName")
public class User {
	
	public static Key<User> key(String userName) {
		return Key.create(User.class, userName);
	}
	
	public User (final String userName, final UserType type, 
				 final String firstName, final String lastName, final String password, final String email) {
		this.userName = userName;
		this.type = type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.ratings = new ArrayList<Rating>();
		this.bids = new ArrayList<Bid>();
	}
	
	@Getter
	@Index
	@Id
	String userName;
	
	@Getter
	UserType type;
	
	@Getter
	String firstName;
	
	@Getter
	String lastName;
	
	@Getter
	@Index
	String password;
	
	@Getter
	String email;
	
	@Getter
	Collection<Rating> ratings;
	
	@Getter
	Collection<Bid> bids;
}
