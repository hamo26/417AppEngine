package com.onlineauction.auction.domain.entity;

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
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.user.domain.entity.User;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(of="id")
public class Auction {
	
	public static Key<Auction> key(long id) {
		return Key.create(Auction.class, id);
	}
	
	@Getter
	@Index
	@Id
	@NonNull
	long id;
	
	@Getter
	@NonNull
	Date startTime;
	
	@Getter
	@NonNull
	Date endTime;
	
	@Getter
	@NonNull
	Item auctionItem;
	
	@Getter
	@NonNull
	User seller;
}
