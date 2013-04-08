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
	 * Place a bid for an auction.
	 * 
	 * @param bid
	 * @param auctionId
	 */
	void placeBidForAuction(Bid bid, long auctionId) throws HgException;
	
	/**
	 * Deletes an auction.
	 * 
	 * @param auctionId
	 */
	void deleteAuction(long auctionId) throws HgException;
	
	/**
	 * Checks if an auction is expired.
	 * 
	 * @param auctionId
	 * @return
	 */
	Boolean isAuctionExpired(long auctionId) throws HgException;
	
	/**
	 * Gets the highest bid for an auction.
	 * 
	 * @param auctionId
	 * @return
	 */
	Bid getHighestBidForAuction(long auctionId) throws HgException;
	
	/**
	 * Get all auctions created by a user.
	 * 
	 * @param userId
	 * @return
	 */
	Collection<Auction> getAuctionsCreatedByUser(String userId);
	
	/**
	 * Gets all auctions a user has placed a bid on.
	 * 
	 * @param userId
	 * @return
	 */
	Collection<Auction> getAuctionsUserHasBidOn(String userId) throws HgException;
	
	/**
	 * Invalidate an auction.
	 * 
	 * @param auctionId
	 * @throws HgException
	 */
	void invalidateAuction(long auctionId) throws HgException;
	
	/**
	 * Checks to see whether an auction is valid.
	 * 
	 * @param auctionId
	 * @return
	 */
	Boolean isAuctionValid(long auctionId) throws HgException;
	
	/**
	 * Delete all user bids from an auction. 
	 * Bids from users who have been deleted should not be displayed on auction.
	 * 
	 * @param userId
	 */
	void deleteUserBidsFromAuctions(String userId) throws HgException;
	
	/**
	 * Invalidate all auctions created by a user.
	 * 
	 * @param userId
	 * @throws HgException
	 */
	void invalidateAuctionsCreatedByUser(String userId) throws HgException;
	
	/**
	 * Cleans up auctions that have ended.
	 */
	void cleanupAuctions();
	
}
