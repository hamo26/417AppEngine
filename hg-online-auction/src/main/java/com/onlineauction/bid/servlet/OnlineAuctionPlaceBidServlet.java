package com.onlineauction.bid.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.auction.servlet.OnlineAuctionSearchAuctionsServlet;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionPlaceBidServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionSearchAuctionsServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;

	@Inject
	private UserService userService;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		try{
			double bidPrice = Double.parseDouble(req.getParameter("bidValue"));
			long auctionId = Long.parseLong((String)req.getParameter("auctionId"));
			log.info("auctionId" + Long.toString(auctionId));
			Item item = auctionService.getAuctionById(auctionId).getAuctionItem();
			log.info("item " + item.getName());
			Bid bid = new Bid(bidPrice, item, auctionId, (String)req.getSession().getAttribute("userName"));
			
			Bid highestBidForAuction = auctionService.getHighestBidForAuction(auctionId);
			
			if (null != highestBidForAuction && bid.getBidPrice() <= highestBidForAuction.getBidPrice()) {
				//Display error alerting user that bid placed needs to be higher.
				//One way of doing this is to set the following 
				//error attribute after checking the post bid action status.
				req.setAttribute("biddingError", Boolean.TRUE);
				log.info("Bid places was not higher than max bid.");
			} else {
				auctionService.placeBidForAuction(bid, auctionId);
				req.setAttribute("biddingError", Boolean.FALSE);
			}
			
			
			req.setAttribute("auctionId", Long.toString(auctionId));
		} catch(HgException e){
			e.printStackTrace();
			log.info("failed to place bid");
		}

		req.getRequestDispatcher("/displayAuction").forward(req, resp);
		
	}
}
