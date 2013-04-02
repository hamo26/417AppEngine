/**
 * Copyright 2012 Google Inc. All Rights Reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionLoginServlet extends HttpServlet {

	@Inject
	private UserService userService;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		
		User user;
		try {
			user = userService.getUserByUserNameAndPassword(userName, password);
			HttpSession session = req.getSession(true);
			session.setAttribute("user", user);
			req.getRequestDispatcher("home/auctionHome.jsp").forward(req, resp);
		} catch (HgException e) {
			req.getRequestDispatcher("/loginAndRegistration/failedLogin.jsp").forward(req, resp);
		}
		
		
	}
}
