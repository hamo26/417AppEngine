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
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.entity.UserType;
import com.onlineauction.user.domain.service.UserService;

@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionRegistrationServlet extends HttpServlet {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionRegistrationServlet.class.getName());
	
	@Inject
	private UserService userService;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		String userName = req.getParameter("userName");
		String password = req.getParameter("password");
		String retypedPassword = req.getParameter("retypedPassword");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String email = req.getParameter("email");
		if(password.equals(retypedPassword)){
			try{
				User newUser = new User(userName, UserType.BUYER, firstName, lastName, password, email);
				
				userService.subscribeUser(newUser);
				
				log.info("Subscribed user with username: " + userName);
				HttpSession session = req.getSession(true);
				session.setAttribute("userName", newUser.getUserName());
				req.getRequestDispatcher("home/auctionHome.jsp").forward(req, resp);
			} catch(Exception e){
				try{
					userService.getUserByUserName(userName);
					req.setAttribute("message", "Username already taken");
				} catch(Exception e2){
					req.setAttribute("message", "Please fill out the registration form correctly");
				}
				req.getRequestDispatcher("loginAndRegistration/failedRegister.jsp").forward(req, resp);
			}
		} else{
			req.setAttribute("message", "Passwords didn't match");
			req.getRequestDispatcher("loginAndRegistration/failedRegister.jsp").forward(req, resp);
		}
	}
}
