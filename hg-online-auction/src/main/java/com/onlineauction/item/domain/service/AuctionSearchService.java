package com.onlineauction.item.domain.service;

import java.util.Collection;

import com.onlineauction.auction.domain.entity.Auction;

public interface AuctionSearchService {
	
	/**
	 * Search for auctions by description.
	 * 
	 * @param description
	 * @return
	 */
	Collection<Auction> searchForAuctionsByDescription(String description);
	
	/**
	 * Search for auctions name.
	 * 
	 * @param name 
	 * @return
	 */
	Collection<Auction> searchForAuctionsByName(String name);
	
	/**
	 * Search for expired auctions by description.
	 * 
	 * @param description
	 * @return
	 */
	Collection<Auction> searchForExpiredAuctionsByDescription(String description);
	
	/**
	 * Search for expired auctions by name.
	 *  
	 * @param name
	 * @return
	 */
	Collection<Auction> searchForExpiredAuctionsByName(String name);
	
	/**
	 * Search for non expired auctions by description.
	 * 
	 * @param description
	 * @return
	 */
	Collection<Auction> searchForNonExpiredAuctionsByDescription(String description);
	
	/**
	 * Search for non expired auctions by name.
	 * 
	 * @param name
	 * @return
	 */
	Collection<Auction> searchForNonExpiredAuctionsByName(String name);
}
