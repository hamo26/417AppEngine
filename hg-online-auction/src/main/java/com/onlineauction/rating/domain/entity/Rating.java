package com.onlineauction.rating.domain.entity;

import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Embed
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="id")
public class Rating {
	
	public static Key<Rating> key(long id) {
		return Key.create(Rating.class, id);
	}
	
	public Rating(final String comment, final Integer rating, final String userName, final Date postingTime) {
		this.comment = comment;
		this.rating = rating;
		this.userName = userName;
		this.postingTime = postingTime;
	}
	
	@Getter
	@Index
	@Id
	@NonNull
	long id;
	
	@Getter
	@NonNull
	String comment;
	
	@Getter
	@NonNull
	Integer rating;
	
	@Getter
	@NonNull
	String userName;
	
	@Getter
	@Index
	@NonNull
	Date postingTime;
}
