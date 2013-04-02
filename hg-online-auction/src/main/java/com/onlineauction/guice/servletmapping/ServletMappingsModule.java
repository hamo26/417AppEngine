package com.onlineauction.guice.servletmapping;

import com.google.inject.servlet.ServletModule;
import com.onlineauction.cron.servlet.OnlineAuctionCleanupServlet;
import com.onlineauction.user.servlet.OnlineAuctionLoginServlet;
import com.onlineauction.user.servlet.OnlineAuctionLogoutServlet;
import com.onlineauction.user.servlet.OnlineAuctionRegistrationServlet;

public class ServletMappingsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		serve("/login").with(OnlineAuctionLoginServlet.class);
		serve("/register").with(OnlineAuctionRegistrationServlet.class);
		serve("/logout").with(OnlineAuctionLogoutServlet.class);
		serve("/cron/cleanAuctions").with(OnlineAuctionCleanupServlet.class);
	}
}
