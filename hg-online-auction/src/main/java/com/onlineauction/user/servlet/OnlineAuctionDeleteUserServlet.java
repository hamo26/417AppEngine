package com.onlineauction.user.servlet;

import java.io.IOException;
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
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionDeleteUserServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionDeleteUserServlet.class.getName());
	
	@Inject
	private UserService userService;
	
	@Inject
	private AuctionService auctionService;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession(false);
		
		String userName = (String) session.getAttribute("userName");
		String confirm = (String) req.getParameter("confirm");
		if(confirm != null && confirm.equals("true")){
			log.info("deleting user " + userName); 
			try {
				auctionService.deleteUserBidsFromAuctions(userName);
				auctionService.invalidateAuctionsCreatedByUser(userName);
				userService.deleteUser(userName);
				session.invalidate();
				log.info("Delete successful");
			} catch (HgException e) {
				//Do nothing. We will be taken to the login and registration page anyways.
				e.printStackTrace();
			}
			
			req.getRequestDispatcher("loginAndRegistration/loginAndRegistrationForm.jsp").forward(req, resp);
		} else{
			log.info("not deleting user due to lack of confirmation");
			req.getRequestDispatcher("/profile").forward(req, resp);
		}
	}
}
