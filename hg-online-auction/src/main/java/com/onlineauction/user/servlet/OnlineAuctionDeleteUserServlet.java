package com.onlineauction.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.auction.exception.HgException;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionDeleteUserServlet extends HttpServlet {
	@Inject
	private UserService userService;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession(false);
		
		String userName = (String) session.getAttribute("userName");
		
		try {
			userService.deleteUser(userName);
			session.invalidate();
		} catch (HgException e) {
			//Do nothing. We will be taken to the login and registration page anyways.
		}

		
		req.getRequestDispatcher("loginAndRegistration/loginAndRegistrationForm.jsp").forward(req, resp);
	}
}
