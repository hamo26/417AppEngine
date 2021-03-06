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
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.auction.servlet.OnlineAuctionDeleteAuctionServlet;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

/**
 * Servlet for getting a user's profile.
 * 
 * @author hamo
 *
 */
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
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		processRequest(req, resp);
	}
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		processRequest(req, resp);
	}
	public void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
			try{
				HttpSession session = req.getSession();
				User user = userService.getUserByUserName((String) session.getAttribute("userName"));
				req.setAttribute("userName", user.getUserName());
				String userId = user.getUserName();
				Collection<Auction> userAuctions = auctionService.getAuctionsCreatedByUser(userId);
				Collection<Auction> bidAuctions = auctionService.getAuctionsUserHasBidOn(userId);
				Collection<Auction> winningBidAuctions = new ArrayList<Auction>();
				Collection<Auction> losingBidAuctions = new ArrayList<Auction>();
				Collection<Auction> wonBidAuctions = new ArrayList<Auction>();
				Collection<Auction> lostBidAuctions = new ArrayList<Auction>();
				Collection<Auction> invalidatedBidAuctions = new ArrayList<Auction>();
				for(Auction auction : bidAuctions){
					if(auction.getIsValid()){
						if(auction.isOver()){
							if(auctionService.getHighestBidForAuction(auction.getId()).getUserId().equals(userId)){
								wonBidAuctions.add(auction);
							} else{
								lostBidAuctions.add(auction);
							}
						} else {
							if(auctionService.getHighestBidForAuction(auction.getId()).getUserId().equals(userId)){
								winningBidAuctions.add(auction);
							} else{
								losingBidAuctions.add(auction);
							}
						}
					} else{
						invalidatedBidAuctions.add(auction);
					}
				}
				
				req.setAttribute("userAuctions", userAuctions);
				req.setAttribute("winningBidAuctions", winningBidAuctions);
				req.setAttribute("losingBidAuctions", losingBidAuctions);
				req.setAttribute("wonBidAuctions", wonBidAuctions);
				req.setAttribute("lostBidAuctions", lostBidAuctions);
				req.setAttribute("invalidatedBidAuctions", invalidatedBidAuctions);
				log.info("Displaying profile");
				req.getRequestDispatcher("/profile/profile.jsp").forward(req, resp);
			} catch(HgException e){
				e.printStackTrace();
			}
		
	}
}
