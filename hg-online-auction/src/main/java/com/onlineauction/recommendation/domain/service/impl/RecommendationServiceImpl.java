package com.onlineauction.recommendation.domain.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.google.inject.Inject;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.recommendation.domain.service.RecommendationService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

/**
 * Implementation of {@link RecommendationService}.
 * 
 * @author hamo
 *
 */
public class RecommendationServiceImpl implements RecommendationService {
	
	@Inject
	private AuctionService auctionService;
	
	@Inject
	private UserService userService;
	
	@Override
	public Collection<Long> getAuctionRecommendations(Long auctionId, int numberOfRecommendation) throws HgException{
		Collection<Long> auctionsResult;
		HashMap<Long, Integer> auctionRecommendationMap = new HashMap<Long, Integer>();
		
		Auction auction = auctionService.getAuctionById(auctionId);
		
		//Get the bids placed on an auction for further analysis.
		Collection<Bid> bidsPlacedOnAuction = auction.getBidsPlaced();
		
		for (Bid bid : bidsPlacedOnAuction) {
			try {
				User bidUser = userService.getUserByUserName(bid.getUserId());
				for (Bid userBid : bidUser.getBids()) {
					Long userBidAuctionId = userBid.getAuctionId();
					if (auctionRecommendationMap.containsKey(userBidAuctionId)) {
						Integer userBidAuctionIdCount = auctionRecommendationMap.get(userBidAuctionId);
						auctionRecommendationMap.put(userBidAuctionId, userBidAuctionIdCount++);
					} else {
						auctionRecommendationMap.put(userBidAuctionId, 1);
					}
				}
			} catch (HgException e) {
				//not being able to load one user shouldn't cause a problem with our algorithm.
				//We will choose to silently ignore this error for now.
			}
		}
		
		ArrayList<Integer> recomendationMapValues = (ArrayList<Integer>) auctionRecommendationMap.values();
		
		if (recomendationMapValues.isEmpty()) {
			//No recommendations to provide.
			auctionsResult = Collections.emptyList();
		} else {
			//If the number of recommendations resulting is smaller than the number
			//of recommendations expected, return all recommendationMapValues.
			if (recomendationMapValues.size() <= numberOfRecommendation) {
				auctionsResult = getRecommendationsFromValues(recomendationMapValues, auctionRecommendationMap);
			} else {
				auctionsResult = getRecommendationsFromValues(recomendationMapValues.subList(0, numberOfRecommendation),
						auctionRecommendationMap);
			}
		}
		
		return auctionsResult;
	}

	private Collection<Long> getRecommendationsFromValues(Collection<Integer> recomendationMapValues,
			HashMap<Long, Integer> auctionRecommendationMap) {
		Collection<Long> recommendationsFromValues = new ArrayList<Long>();
		
		Set<Entry<Long,Integer>> entrySet = auctionRecommendationMap.entrySet();
		for (Entry<Long, Integer> entry : entrySet) {
			if (recomendationMapValues.contains(entry.getValue())) {
				recommendationsFromValues.add(entry.getKey());
			}
		}
		
		return recommendationsFromValues;
	}

}
