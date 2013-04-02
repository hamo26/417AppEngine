package com.onlineauction.cron.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.item.domain.service.AuctionService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionCleanupServlet extends HttpServlet {
	@Inject
	private AuctionService auctionService;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Logger log = Logger.getLogger(OnlineAuctionCleanupServlet.class.getName());
		
		log.info("Cleaning up expired auctions in datastore...\n");
		auctionService.cleanupAuctions();
	}
}
