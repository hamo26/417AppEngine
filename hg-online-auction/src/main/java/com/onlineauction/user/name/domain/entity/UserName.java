package com.onlineauction.user.name.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="id")
public class UserName {
	
	public static Key<UserName> key(String userName) {
		return Key.create(UserName.class, userName);
	}
	
	
	@Getter
	@Index
	@Id
	Long id;
	
	@Getter
	@Index
	@NonNull
	String userName;
	
}
