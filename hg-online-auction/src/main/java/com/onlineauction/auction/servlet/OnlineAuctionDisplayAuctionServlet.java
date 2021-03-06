package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import com.onlineauction.recommendation.domain.service.RecommendationService;

/**
 * Servlet used to handle displaying auction information on jsp.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionDisplayAuctionServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteAuctionServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;

	@Inject
	private RecommendationService recommendationService;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		processRequest(req, resp);
	}
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		processRequest(req, resp);
	}
	
	private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
			try {
				long auctionId = Long.parseLong((String) req.getAttribute("auctionId"));
				log.info("auctionID: " + Long.toString(auctionId));
				Auction auction = auctionService.getAuctionById(auctionId);
				if(auction != null) {
					Item item = auction.getAuctionItem();
					DecimalFormat decFormat = new DecimalFormat("0.00");
					decFormat.setGroupingUsed(false);
					Collection<Auction> recAuctions = new ArrayList<Auction>();
					try{
						Collection<Long> recIds = recommendationService.getAuctionRecommendations(auctionId, 2);
						try{
						for(long recId : recIds){
							recAuctions.add(auctionService.getAuctionById(recId));
						}
						} catch(Exception e){
							e.printStackTrace();
						}
					} catch(Exception e){
						//probably no bids
						e.printStackTrace();
					}
					req.setAttribute("recAuctions", recAuctions);
					req.setAttribute("auctionId", Long.toString(auctionId));
					req.setAttribute("sellerId", auction.getSellerId());
					req.setAttribute("itemName", item.getName());
					req.setAttribute("itemDescription", item.getDescription());
					req.setAttribute("itemBasePrice", decFormat.format(item.getBasePrice()));
					if(!auction.getIsValid()){
						req.setAttribute("invalidated", "true");
					}
					try{
						Bid highestBid = auctionService.getHighestBidForAuction(auction.getId());
						req.setAttribute("maxBidPrice", decFormat.format(highestBid.getBidPrice()));
						req.setAttribute("maxBidUsername", highestBid.getUserId());
					}catch(Exception e){
						//no bids
					}
					
					DateFormat df = new SimpleDateFormat("EEE MMM dd, yyyy 'at' hh:mma");
					req.setAttribute("startTime", df.format(auction.getStartTime()));
					req.setAttribute("endTime", df.format(auction.getEndTime()));
					
					if(auction.isOver()){
						req.setAttribute("isOver", true);
					}
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
