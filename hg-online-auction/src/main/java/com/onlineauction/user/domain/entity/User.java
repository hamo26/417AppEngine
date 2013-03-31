package com.onlineauction.user.domain.entity;

import java.util.Collection;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.rating.domain.entity.Rating;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="userName")
public class User {
	
	public static Key<User> key(String userName) {
		return Key.create(User.class, userName);
	}
	
	
	@Getter
	@Index
	@Id
	@NonNull
	String userName;
	
	@Getter
	@NonNull
	UserType type;
	
	@Getter
	@NonNull
	String firstName;
	
	@Getter
	@NonNull
	String lastName;
	
	@Getter
	@Index
	@NonNull
	String password;
	
	@Getter
	@NonNull
	String email;
	
	@Getter
	@Setter
	Collection<Rating> ratings;
	
	@Getter
	@Setter
	Collection<Bid> bids;
}
