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
import com.onlineauction.item.domain.service.AuctionSearchService;

/**
 * Servlet used to handle searching for auction from jsp.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionSearchAuctionsServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionSearchAuctionsServlet.class.getName());
	
	@Inject
	private AuctionSearchService auctionSearchService;

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String searchType = (String) req.getParameter("searchType");
		String searchTerm = (String) req.getParameter("searchTerm");
		String searchExpired = (String) req.getParameter("searchExpired");
		
		log.info("Searching \"" + searchTerm + "\" " + searchType);
		Collection<Auction> auctions;
		
		if (searchType.equals("byDescription")) {
			if(searchExpired.equals("ongoing")){
				log.info("Searching for ongoing auctions by description with: " + searchTerm);
				auctions = auctionSearchService.searchForNonExpiredAuctionsByDescription(searchTerm);
			} else if(searchExpired.equals("expired")){
				log.info("Searching for expired auctions by description with: " + searchTerm);
				auctions = auctionSearchService.searchForExpiredAuctionsByDescription(searchTerm);
			} else if(searchExpired.equals("both")){
				log.info("Searching for auctions by description with: " + searchTerm);
				auctions = auctionSearchService.searchForAuctionsByDescription(searchTerm);
			} else {
				log.severe("invalid searchExpired");
				auctions = null;
			}
			
		} else if(searchType.equals("byName")) {
			if(searchExpired.equals("ongoing")){
				log.info("Searching for ongoing auctions by name with: " + searchTerm);
				auctions = auctionSearchService.searchForNonExpiredAuctionsByName(searchTerm);
			} else if(searchExpired.equals("expired")){
				log.info("Searching for expired auctions by name with: " + searchTerm);
				auctions = auctionSearchService.searchForExpiredAuctionsByName(searchTerm);
			} else if(searchExpired.equals("both")){
				log.info("Searching for auctions by name with: " + searchTerm);
				auctions = auctionSearchService.searchForAuctionsByName(searchTerm);
			} else {
				log.severe("invalid searchExpired");
				auctions = null;
			}
		} else {
			auctions = null;
			log.severe("invalid searchType");
		}
		
		req.setAttribute("auctionResults", auctions);
		req.setAttribute("displayAuctions", "true");
		req.getRequestDispatcher("/auction/auctionSearch.jsp").forward(req, resp);
		
	}
}
