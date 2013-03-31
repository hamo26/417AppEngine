package com.onlineauction.auction.domain.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.user.domain.entity.User;

@Entity
@NoArgsConstructor
@EqualsAndHashCode(of="id")
public class Auction {
	
	public static Key<Auction> key(long id) {
		return Key.create(Auction.class, id);
	}
	
	public Auction(final Date startTime, final Date endTime, 
				   final Item auctionItem, final User seller) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.auctionItem = auctionItem;
		this.seller = seller;
		this.bidsPlaced = new ArrayList<Bid>();
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
	
	@Getter
	Collection<Bid> bidsPlaced;
}
