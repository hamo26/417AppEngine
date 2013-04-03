package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;
import com.onlineauction.user.servlet.OnlineAuctionRegistrationServlet;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionCreateAuctionServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionRegistrationServlet.class.getName());
	
	
	@Inject
	private UserService userService;
	@Inject
	private AuctionService auctionService;

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String userName = (String)session.getAttribute("userName");
		try {
			//TODO:add params to item
			Item item = new Item();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mma");
			String endTimeString = (String) req.getParameter("endTime");
			Date endTime = sdf.parse(endTimeString);
			long auctionId = auctionService.createAuction(userName, item, endTime);
			Auction auction = auctionService.getAuctionById(auctionId);
			req.setAttribute("auction", auction);
		} catch (HgException e) {
			log.warning("Create auction failed");
		} catch (java.text.ParseException e){
			log.warning("failed to parse date");
		}
		req.getRequestDispatcher("/auction/displayAuction.jsp").forward(req, resp);
		
	}
}
