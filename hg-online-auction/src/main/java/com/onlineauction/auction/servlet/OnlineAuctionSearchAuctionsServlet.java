package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.item.domain.service.AuctionService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionSearchAuctionsServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionSearchAuctionsServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;

	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String searchType = (String) req.getAttribute("searchType");
		String searchTerm = (String) req.getAttribute("searchTerm");
		
		log.info("Searching \"" + searchTerm + "\" " + searchType);
		Collection<Auction> auctions;
		
		if (searchType.equals("byDescription")) {
			log.info("Searching for auctions by description with: " + searchTerm);
			auctions = auctionService.searchForAuctionsByDescription(searchTerm);
		} else if(searchType.equals("byType")) {
			log.info("Searching for auctions by name with: " + searchTerm);
			auctions = auctionService.searchForAuctionsByName(searchTerm);
		} else {
			auctions = null;
			log.severe("invalid searchType");
		}
		
		req.setAttribute("auctionResults", auctions);
			
		req.getRequestDispatcher("/auction/auctionSearch.jsp").forward(req, resp);
		
	}
}
