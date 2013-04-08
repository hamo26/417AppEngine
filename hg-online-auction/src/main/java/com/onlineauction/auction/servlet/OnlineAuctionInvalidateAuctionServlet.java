package com.onlineauction.auction.servlet;

import java.io.IOException;

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
public class OnlineAuctionInvalidateAuctionServlet extends HttpServlet {
	@Inject
	private AuctionService auctionService;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String auctionIdString = (String) req.getParameter("auctionId");
		long auctionId = Long.parseLong(auctionIdString); 
		try {
			auctionService.invalidateAuction(auctionId);
		} catch (HgException e) {
			e.printStackTrace();
		}
		req.setAttribute("auctionId", auctionIdString);
		//forward some where.
		req.getRequestDispatcher("/displayAuction").forward(req, resp);
		
	}
}
