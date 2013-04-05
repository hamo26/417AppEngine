package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.item.domain.service.AuctionService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionDeleteAuctionServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteAuctionServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		Long auctionId = (Long) req.getAttribute("auctionId");
		
		try {
			auctionService.deleteAuction(auctionId);
			log.info("Deleting auction with id: " + auctionId);
		} catch (HgException e) {
			log.warning("Failed to delete auction with id: " + auctionId);
		}
		
		//forward some where.
		//req.getRequestDispatcher("/auction/auctionSearchResults.jsp").forward(req, resp);
		
	}
}
