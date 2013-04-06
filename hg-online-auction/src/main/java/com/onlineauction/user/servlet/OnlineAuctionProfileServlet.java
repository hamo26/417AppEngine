package com.onlineauction.user.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.auction.servlet.OnlineAuctionDeleteAuctionServlet;
import com.onlineauction.bid.domain.entity.Bid;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionProfileServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteAuctionServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;
	
	@Inject
	private UserService userService;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
			try{
				HttpSession session = req.getSession();
				User user = userService.getUserByUserName((String) session.getAttribute("userName"));
				req.setAttribute("userName", user.getUserName());
				String userId = user.getUserName();
				Collection<Bid> bids = user.getBids();
				Collection<Bid> winningBids = new ArrayList<Bid>();
				Collection<Bid> losingBids = new ArrayList<Bid>();
				if(bids != null){
					for(Bid bid : bids){
						//check if the highest bid is by the current user
						try{
							if(userId.equals(auctionService.getHighestBidForAuction(bid.getAuctionId()).getUserId())){
								winningBids.add(bid);
							}else{
								losingBids.add(bid);
							}
						} catch (HgException e){
							e.printStackTrace();
						}
					}
				}
				// need a way to get all the user's auctions Collection<Auction> auctions = auctionService;
				
				req.setAttribute("winningBids", winningBids);
				req.setAttribute("losingBids", losingBids);
				log.info("Displaying profile");
				req.getRequestDispatcher("/profile/profile.jsp").forward(req, resp);
			} catch(HgException e){
				e.printStackTrace();
			}
		
	}
}