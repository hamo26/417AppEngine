package com.onlineauction.user.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Singleton;

/**
 * Servlet for logging out users.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionLogoutServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession(true);
		session.invalidate();
		req.getRequestDispatcher("loginAndRegistration/logout.jsp").forward(req, resp);
	}
}
