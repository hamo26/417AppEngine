package com.onlineauction.auction.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.domain.entity.Auction;
import com.onlineauction.item.domain.entity.Item;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.service.UserService;

/**
 * Servlet used to handle jsp creation of {@link Auction}.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionCreateAuctionServlet extends HttpServlet {
	
	@Inject
	private UserService userService;
	
	@Inject
	private AuctionService auctionService;

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		HttpSession session = req.getSession(false);
		String userName = (String)session.getAttribute("userName");
		
		String itemName = req.getParameter("itemName");
		String itemDescription = req.getParameter("itemDescription");
		String itemBasePriceString = req.getParameter("itemBasePrice");
		String endTimeString = (String) req.getParameter("endTime");
		
		Item item;
		Double itemBasePrice = 0.0;
		Date endTime = null;
		boolean success = true;
		
		if(itemName.isEmpty()){
			req.setAttribute("itemNameError", "true");
			success = false;
		}
		
		try{
			itemBasePrice = Double.valueOf(itemBasePriceString);
		} catch(NumberFormatException e){
			//invalid base price
			req.setAttribute("basePriceError", "true");
			success = false;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mma");
		try {
			endTime = sdf.parse(endTimeString);
		} catch (ParseException e) {
			//invalid end time
			req.setAttribute("endTimeError", "true");
			success = false;
		}
		if(success){
			item = new Item(itemName, itemDescription, itemBasePrice);
			long auctionId = auctionService.createAuction(userName, item, endTime);
			req.setAttribute("auctionId", Long.toString(auctionId));
			req.getRequestDispatcher("/displayAuction").forward(req, resp);
		} else{
			req.setAttribute("createError", "true");
			req.getRequestDispatcher("/auction/createAuctionForm.jsp").forward(req, resp);
		}
		
		
	}
}
