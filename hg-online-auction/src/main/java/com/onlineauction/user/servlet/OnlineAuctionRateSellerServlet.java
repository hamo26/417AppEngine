package com.onlineauction.user.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.auction.servlet.OnlineAuctionDeleteAuctionServlet;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.rating.domain.entity.Rating;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionRateSellerServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteAuctionServlet.class.getName());
	
	@Inject
	private AuctionService auctionService;
	
	@Inject
	private UserService userService;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
		try {
			Integer rating = Integer.parseInt( (String) req.getParameter("ratingInput"));
			String comment = (String) req.getParameter("ratingComment");
			
			long auctionId = Long.parseLong((String) req.getParameter("auctionId"));
			req.setAttribute("auctionId", auctionId);
			
			Auction auction = auctionService.getAuctionById(auctionId);
			
			
			userService.rateUser(auction.getSellerId(), new Rating(comment, rating, 
					(String)req.getSession().getAttribute("userName"), new Date()));
		} catch (HgException e) {
			e.printStackTrace();
			log.info("failed to place rating");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("failed to place rating");
		}
		
		
		req.getRequestDispatcher("/displayAuction").forward(req, resp);
	}
}
