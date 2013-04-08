package com.onlineauction.item.domain.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import com.google.inject.Inject;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.objectify.HgDataService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

public class AuctionServiceImpl implements AuctionService {
	
	private UserService userService;
	
	@Inject
	public AuctionServiceImpl(final UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public long createAuction(String userId, Item item, Date endTime) {
		
		Auction auction = new Auction(new Date(), endTime, item, userId, Boolean.TRUE);
		
		HgDataService.objectify()
					 .save()
					 .entity(auction)
					 .now();
		
		return auction.getId();
		
	}

	@Override
	public Auction getAuctionById(long auctionId) throws HgException{
		Auction auction = HgDataService.objectify()
					 .load()
					 .type(Auction.class)
					 .filter("id ==", auctionId)
					 .first()
					 .get();
		
		if (auction == null) {
			throw new HgException("Auction with " + auctionId + " does not exist");
		}
		
		return auction;
	}

	
	@Override
	public void placeBidForAuction(Bid bid, long auctionId) throws HgException{
		Auction auctionById = getAuctionById(auctionId);
		
		if (!auctionById.getIsValid()) {
			throw new HgException("Auction is invalid");
		}
		if (auctionById.getBidsPlaced() == null) {
			auctionById.setBidsPlaced(new ArrayList<Bid>());
		}
		
		auctionById.getBidsPlaced().add(bid);
		
		String userId = bid.getUserId();
		userService.addUserBid(userId, bid);
		
		HgDataService.objectify()
					 .save()
					 .entities(auctionById);
	}

	@Override
	public void cleanupAuctions() {
		Date currentDate = new Date();
		
		List<Auction> auctions = HgDataService.objectify()
					 .load()
					 .type(Auction.class)
					 .filter("endTime < ", currentDate)
					 .list();
		
		if (!auctions.isEmpty()) {
			HgDataService.objectify()
					     .delete()
					     .entities(auctions);
		}
	}

	@Override
	public void deleteAuction(long auctionId) throws HgException {
		Auction auctionById = getAuctionById(auctionId);
		
		HgDataService.objectify()
					 .delete()
					 .entity(auctionById);
	}

	@Override
	public Boolean isAuctionExpired(long auctionId) throws HgException {
		Auction auction = getAuctionById(auctionId);
		
		return auction.isOver();
	}

	@Override
	public Bid getHighestBidForAuction(long auctionId) throws HgException {
		Auction auction = getAuctionById(auctionId);
		Bid highestBid = null;
		
		Collection<Bid> bidsPlaced = auction.getBidsPlaced();
		if (null != bidsPlaced) {
			ArrayList<Bid> bidsPlacedToSort = new ArrayList<Bid>(bidsPlaced);
			
			Collections.sort(bidsPlacedToSort, new Comparator<Bid>() {
				
				@Override
				public int compare(Bid bid1, Bid bid2) {
					return bid1.getBidPrice().compareTo(bid2.getBidPrice());
				}
			});
			
			highestBid = bidsPlacedToSort.get(bidsPlacedToSort.size() - 1);
		} 
		
		return highestBid;
	}

	@Override
	public Collection<Auction> getAuctionsCreatedByUser(String userId) {
		return HgDataService.objectify()
		 .load()
		 .type(Auction.class)
		 .filter("sellerId ==", userId)
		 .list();
	}

	@Override
	public Collection<Auction> getAuctionsUserHasBidOn(String userId) throws HgException {
		User user = userService.getUserByUserName(userId);
		HashSet<Auction> auctions = new HashSet<Auction>();

		if (user.getBids() != null) {
			for (Bid bid : user.getBids()) {
				auctions.add(getAuctionById(bid.getAuctionId()));
			}
		}
		
		return auctions;
	}

	@Override
	public Boolean isAuctionValid(long auctionId) throws HgException {
		Auction auction = getAuctionById(auctionId);
		
		return auction.getIsValid();
	}

	@Override
	public void invalidateAuction(long auctionId) throws HgException {
		Auction auction = getAuctionById(auctionId);
		
		auction.setIsValid(Boolean.FALSE);
		
		HgDataService.objectify()
		 			 .save()
		 			 .entity(auction)
		 			 .now();
	}

	@Override
	public void deleteUserBidsFromAuctions(String userId) throws HgException {
		User user = userService.getUserByUserName(userId);
		
		if (user.getBids() != null) {
			for (Bid bid : user.getBids()) {
				try {
					Auction auction = getAuctionById(bid.getAuctionId());
					if (auction.getIsValid()) {
						auction.getBidsPlaced().remove(bid);
					}
				} catch (HgException e) {
					//If an auction was not found for the bid id. Continue and disregard.
					//The auction may have been deleted.
				}
			}
		}
	}

	@Override
	public void invalidateAuctionsCreatedByUser(String userId)
			throws HgException {
		Collection<Auction> auctionsCreatedByUser = getAuctionsCreatedByUser(userId);
		
		for (Auction auction : auctionsCreatedByUser) {
			auction.setIsValid(Boolean.FALSE);
		}
		
		HgDataService.objectify()
					 .save()
					 .entities(auctionsCreatedByUser)
					 .now();
	}
}
