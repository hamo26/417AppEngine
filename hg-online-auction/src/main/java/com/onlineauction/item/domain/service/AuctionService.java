package com.onlineauction.item.domain.service;

import java.util.Collection;
import java.util.Date;

import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;

public interface AuctionService {

	/**
	 * Create an auction. 
	 * 
	 * @param userId
	 * @param item
	 * @param endTime
	 */
	long createAuction(String userId, Item item, Date endTime);
	
	/**
	 * Get an auction by id.
	 * 
	 * @param auctionId
	 * @return
	 */
	Auction getAuctionById(long auctionId) throws HgException; 
	
	/**
	 * Search for an auction by its description.
	 * 
	 * @param description
	 * @return
	 */
	Collection<Auction> searchForAuctionsByDescription(String description);
	
	/**
	 * Search for auctions by item name.
	 * 
	 * @param itemName
	 * @return
	 */
	Collection<Auction> searchForAuctionByItemName(String itemName);
	
	/**
	 * Place a bid for an auction.
	 * 
	 * @param bid
	 * @param auctionId
	 */
	void placeBidForAuction(Bid bid, long auctionId) throws HgException;
	
	/**
	 * Cleans up auctions that have ended.
	 */
	void cleanupAuctions();
	
}
