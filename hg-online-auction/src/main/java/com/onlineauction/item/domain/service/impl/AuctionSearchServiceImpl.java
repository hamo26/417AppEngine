package com.onlineauction.item.domain.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.item.domain.service.AuctionSearchService;
import com.onlineauction.objectify.HgDataService;

public class AuctionSearchServiceImpl implements AuctionSearchService {

	@Override
	public Collection<Auction> searchForAuctionsByDescription(String description) {
		return HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.description >=", description)
				 .filter("auctionItem.description <=", description + "\ufffd")
				 .list();
	}

	@Override
	public Collection<Auction> searchForAuctionsByName(String name) {
		return HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.name >=", name)
				 .filter("auctionItem.name <=", name + "\ufffd")
				 .list();
	}

	@Override
	public Collection<Auction> searchForExpiredAuctionsByDescription(
			String description) {
		List<Auction> auctions = HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.description >=", description)
				 .filter("auctionItem.description <=", description + "\ufffd")
				 .list();
		
		return getExpiredAuctions(auctions);
	}

	@Override
	public Collection<Auction> searchForExpiredAuctionsByName(String name) {
		List<Auction> auctions = HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.name >=", name)
				 .filter("auctionItem.name <=", name + "\ufffd")
				 .list();
	
		return getExpiredAuctions(auctions);
	}

	@Override
	public Collection<Auction> searchForNonExpiredAuctionsByDescription(
			String description) {
		List<Auction> auctions = HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.description >=", description)
				 .filter("auctionItem.description <=", description + "\ufffd")
				 .list();
	
		return removedExpiredAuctions(auctions);
	}

	@Override
	public Collection<Auction> searchForNonExpiredAuctionsByName(String name) {
		List<Auction> auctions = HgDataService.objectify()
				 .load()
				 .type(Auction.class)
				 .filter("auctionItem.name >=", name)
				 .filter("auctionItem.name <=", name + "\ufffd")
				 .list();
	
		return removedExpiredAuctions(auctions);
	}
	
	private Collection<Auction> removedExpiredAuctions(final Collection<Auction> auctions) {
		Collection<Auction> cleanedAuctions = new ArrayList<Auction>();
		
		Date currentDate = new Date();
		for (Auction auction : auctions) {
			if (auction.getEndTime().after(currentDate)) {
				cleanedAuctions.add(auction);
			}
		}
		
		return cleanedAuctions;
	}
	
	private Collection<Auction> getExpiredAuctions(final Collection<Auction> auctions) {
		Collection<Auction> expiredAuctions = new ArrayList<Auction>();
		
		Date currentDate = new Date();
		for (Auction auction : auctions) {
			if (auction.getEndTime().before(currentDate)) {
				expiredAuctions.add(auction);
			}
		}
		
		return expiredAuctions;
	}

}
