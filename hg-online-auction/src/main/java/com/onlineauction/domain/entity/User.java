package com.onlineauction.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class User {
	
	public static Key<User> key(long id) {
		return Key.create(User.class, id);
	}
	
	@Id
	@Getter
	long id;
	
	@Getter
	String userName;
	
	@Getter
	UserType type;
	
	@Getter
	String firstName;
	
	@Getter
	String lastName;
	
	@Getter 
	String password;
	
	@Getter
	String email;
}
