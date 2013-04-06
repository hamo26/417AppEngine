package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionDisplayAuctionServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteAuctionServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

			
			try {
				long auctionId = (Long)req.getAttribute("auctionId");
				log.info("auctionID: " + Long.toString(auctionId));
				Auction auction = auctionService.getAuctionById(auctionId);
				if(auction != null) {
					Item item = auction.getAuctionItem();
					
					req.setAttribute("auctionId", Long.toString(auctionId));
					req.setAttribute("itemName", item.getName());
					req.setAttribute("itemDescription", item.getDescription());
					req.setAttribute("itemBasePrice", item.getBasePrice());
					
					try{
						Bid highestBid = auctionService.getHighestBidForAuction(auction.getId());
						req.setAttribute("maxBidPrice", highestBid.getBidPrice());
						req.setAttribute("maxBidUsername", highestBid.getUserId());
					}catch(Exception e){
						//no bids
					}
					
					DateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy 'at' hh:mma");
					req.setAttribute("startTime", df.format(auction.getStartTime()));
					req.setAttribute("endTime", df.format(auction.getEndTime()));
					
					log.info("valid auction. Displaying");
					req.setAttribute("validAuction", "True");
				}
			} catch (HgException e1) {
				// failed to get auction by id
				e1.printStackTrace();
			} catch (NumberFormatException e){
				e.printStackTrace();
			}
		
		//forward some where.
		req.getRequestDispatcher("/auction/displayAuction.jsp").forward(req, resp);
		
	}
}
