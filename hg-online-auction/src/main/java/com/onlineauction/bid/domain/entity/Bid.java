package com.onlineauction.bid.domain.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.item.domain.entity.Item;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="id")
@Embed
public class Bid {
	
	public static Key<Bid> key(long id) {
		return Key.create(Bid.class, id);
	}
	
	@Getter
	@Index
	@Id
	Long id;
	
	@Getter
	@NonNull
	Double bidPrice;
	
	@Getter
	@NonNull
	Item item;
	
	@Getter
	@NonNull
	Long auctionId;
	
	@Getter
	@NonNull
	String userId;
}
