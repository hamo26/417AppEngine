package com.onlineauction.recommendation.domain.service;

import java.util.Collection;

import com.onlineauction.auction.exception.HgException;

/**
 * Service for auction recommendations. 
 * 
 * @author hamo
 *
 */
public interface RecommendationService {

	/**
	 * Get a series of recommended auctions based on the auction currently being viewed
	 * and the the bids other users have placed.
	 * 
	 * @param auction
	 * @param numberOfRecommendations
	 * @return
	 */
	Collection<Long> getAuctionRecommendations(Long auctionId, int numberOfRecommendations) throws HgException;
}
