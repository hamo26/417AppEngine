package com.onlineauction.guice.servletmapping;

import com.google.inject.servlet.ServletModule;
import com.onlineauction.auction.filter.OnlineAuctionLoginFilter;
import com.onlineauction.auction.servlet.OnlineAuctionCreateAuctionServlet;
import com.onlineauction.auction.servlet.OnlineAuctionDeleteAuctionServlet;
import com.onlineauction.auction.servlet.OnlineAuctionDisplayAuctionServlet;
import com.onlineauction.auction.servlet.OnlineAuctionInvalidateAuctionServlet;
import com.onlineauction.auction.servlet.OnlineAuctionSearchAuctionsServlet;
import com.onlineauction.bid.servlet.OnlineAuctionPlaceBidServlet;
import com.onlineauction.cron.servlet.OnlineAuctionCleanupServlet;
import com.onlineauction.user.servlet.OnlineAuctionDeleteUserServlet;
import com.onlineauction.user.servlet.OnlineAuctionLoginServlet;
import com.onlineauction.user.servlet.OnlineAuctionLogoutServlet;
import com.onlineauction.user.servlet.OnlineAuctionProfileServlet;
import com.onlineauction.user.servlet.OnlineAuctionRateSellerServlet;
import com.onlineauction.user.servlet.OnlineAuctionRegistrationServlet;
import com.onlineauction.user.servlet.OnlineAuctionUserProfileServlet;

public class ServletMappingsModule extends ServletModule {

	@Override
	protected void configureServlets() {
		filter("/*").through(OnlineAuctionLoginFilter.class);
		
		serve("/login").with(OnlineAuctionLoginServlet.class);
		serve("/register").with(OnlineAuctionRegistrationServlet.class);
		serve("/logout").with(OnlineAuctionLogoutServlet.class);
		serve("/cron/cleanAuctions").with(OnlineAuctionCleanupServlet.class);
		serve("/createAuction").with(OnlineAuctionCreateAuctionServlet.class);
		serve("/searchAuctions").with(OnlineAuctionSearchAuctionsServlet.class);
		serve("/deleteAuction").with(OnlineAuctionDeleteAuctionServlet.class);
		serve("/displayAuction").with(OnlineAuctionDisplayAuctionServlet.class);
		serve("/profile").with(OnlineAuctionProfileServlet.class);
		serve("/placeBid").with(OnlineAuctionPlaceBidServlet.class);
		serve("/rateSeller").with(OnlineAuctionRateSellerServlet.class);
		serve("/userProfile").with(OnlineAuctionUserProfileServlet.class);
		serve("/unsubscribeUser").with(OnlineAuctionDeleteUserServlet.class);
		serve("/invalidateAuction").with(OnlineAuctionInvalidateAuctionServlet.class);
	}
}
