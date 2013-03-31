package com.onlineauction.item.domain.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.objectify.HgDataService;
import com.onlineauction.user.domain.entity.User;

public class AuctionServiceImpl implements AuctionService {

	@Override
	public void createAuction(User user, Item item, Date endTime) {
		
		Auction auction = new Auction(new Date(), endTime, item, user);
		
		HgDataService.objectify()
					 .save()
					 .entity(auction);
	}

	@Override
	public Auction getAuctionById(long auctionId) {
		Auction auction = HgDataService.objectify()
					 .load()
					 .type(Auction.class)
					 .filter("id ==", auctionId)
					 .first().get();
		
		if (auction == null) {
			//throw an exception stating that auction was not found.
		}
		
		return auction;
	}

	@Override
	public Collection<Auction> searchForAuctionsByDescription(String description) {
		return null;
	}

	@Override
	public Collection<Auction> searchForAuctionByItemName(String itemName) {
		return null;
	}

	@Override
	public void placeBidForAuction(Bid bid, long auctionId) {
		// TODO Auto-generated method stub
		Auction auctionById = getAuctionById(auctionId);
		auctionById.getBidsPlaced().add(bid);
		
		User user = bid.getUser();
		user.getBids().add(bid);
		
		HgDataService.objectify()
					 .save()
					 .entities(auctionById, user);
	}

	@Override
	public void cleanupAuctions() {
		Date currentDate = new Date();
		
		List<Auction> auctions = HgDataService.objectify()
					 .load()
					 .type(Auction.class)
					 .filter("endDate <= ", currentDate)
					 .list();
		
		if (!auctions.isEmpty()) {
			HgDataService.objectify()
					     .delete()
					     .entities(auctions);
		}
	}

}
