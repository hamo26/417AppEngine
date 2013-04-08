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
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.onlineauction.user.domain.entity.User;
import com.onlineauction.user.domain.entity.UserType;
import com.onlineauction.user.domain.service.UserService;
import com.onlineauction.user.name.domain.service.UserNameService;

/**
 * Servlet for registering users.
 * 
 * @author hamo
 *
 */
@SuppressWarnings("serial")
@Singleton
public class OnlineAuctionRegistrationServlet extends HttpServlet {
	private static final String EMAIL = "email";

	private static final String LAST_NAME = "lastName";

	private static final String FIRST_NAME = "firstName";

	private static final String PASSWORD = "password";

	private static final String USER_NAME = "userName";

	private static final Logger log = Logger
			.getLogger(OnlineAuctionRegistrationServlet.class.getName());
	
	@Inject
	private UserService userService;
	
	@Inject
	private UserNameService userNameService;
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		Collection<String> missingFields = new ArrayList<String>();
		String userName = req.getParameter(USER_NAME);
		if (StringUtil.isEmptyOrWhitespace(userName)) {
			missingFields.add(USER_NAME);
		}
		
		String password = req.getParameter(PASSWORD);
		if(StringUtil.isEmptyOrWhitespace(password)) {
			missingFields.add(PASSWORD);
		}
		
		String retypedPassword = req.getParameter("retypedPassword");
		
		String firstName = req.getParameter(FIRST_NAME);
		if (StringUtil.isEmptyOrWhitespace(firstName)) {
			missingFields.add(FIRST_NAME);
		}
		
		String lastName = req.getParameter(LAST_NAME);
		if (StringUtil.isEmptyOrWhitespace(lastName)) {
			missingFields.add(LAST_NAME);
		}
		
		String email = req.getParameter(EMAIL);
		if (StringUtil.isEmptyOrWhitespace(email)) {
			missingFields.add(EMAIL);
		}
		
		if(password.equals(retypedPassword)){
			try{
				
				if (!missingFields.isEmpty()) {
					req.setAttribute("message", "Missing fields: " + missingFields.toString());
					req.getRequestDispatcher("loginAndRegistration/failedRegister.jsp").forward(req, resp);
				} else if (!userNameService.isUserNameValidForRegistration(userName)) {
					req.setAttribute("message", "Username already taken");
					req.getRequestDispatcher("loginAndRegistration/failedRegister.jsp").forward(req, resp);
				} else { 
					User newUser = new User(userName, UserType.BUYER, firstName, lastName, password, email);
					userNameService.addUserName(userName);
					userService.subscribeUser(newUser);
					
					log.info("Subscribed user with username: " + userName);
					HttpSession session = req.getSession(true);
					session.setAttribute(USER_NAME, newUser.getUserName());
					session.setAttribute(FIRST_NAME, newUser.getFirstName());
					session.setAttribute(LAST_NAME, newUser.getLastName());
					session.setAttribute(EMAIL, newUser.getEmail());
					req.getRequestDispatcher("home/auctionHome.jsp").forward(req, resp);
				}
			} catch(Exception e){
				e.printStackTrace();
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

