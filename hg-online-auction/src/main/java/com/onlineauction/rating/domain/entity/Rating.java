package com.onlineauction.rating.domain.entity;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.user.domain.entity.User;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="id")
public class Rating {
	
	public static Key<Rating> key(long id) {
		return Key.create(Rating.class, id);
	}
	
	@Getter
	@Index
	@Id
	@NonNull
	long id;
	
	@Getter
	@NonNull
	String description;
	
	@Getter
	@NonNull
	Integer rating;
	
	@Getter
	@NonNull
	User user;
	
	@Getter
	@Index
	@NonNull
	Date postingTime;
}
