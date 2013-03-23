package com.onlineauction.guice.servletmapping;

import com.google.inject.Scopes;
import com.google.inject.servlet.ServletModule;
import com.onlineauction.user.servlet.OnlineAuctionLoginAndRegistrationServlet;

public class ServletMappingsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		bind(OnlineAuctionLoginAndRegistrationServlet.class).in(Scopes.SINGLETON);
		serve("/login").with(OnlineAuctionLoginAndRegistrationServlet.class);
	}
}
