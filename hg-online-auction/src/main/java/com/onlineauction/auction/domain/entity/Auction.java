package com.onlineauction.auction.domain.entity;

import java.util.Collection;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;

@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Auction {
	
	public static Key<Auction> key(long id) {
		return Key.create(Auction.class, id);
	}
	
	@Getter
	@Index
	@Id
	Long id;
	
	@Getter
	@NonNull
	Date startTime;
	
	@Getter
	@NonNull
	@Index
	Date endTime;
	
	@Getter
	@NonNull
	Item auctionItem;
	
	@Getter
	@NonNull
	String sellerId;
	
	@Getter
	@Setter
	@Embed
	Collection<Bid> bidsPlaced;
	
	public boolean isOver(){
		return (new Date()).after(this.endTime);
	}
}
