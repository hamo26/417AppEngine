package com.onlineauction.guice.servicemappings;

import com.google.inject.AbstractModule;
import com.onlineauction.item.domain.service.AuctionSearchService;
import com.onlineauction.item.domain.service.AuctionService;
import com.onlineauction.item.domain.service.impl.AuctionSearchServiceImpl;
import com.onlineauction.item.domain.service.impl.AuctionServiceImpl;
import com.onlineauction.recommendation.domain.service.RecommendationService;
import com.onlineauction.recommendation.domain.service.impl.RecommendationServiceImpl;
import com.onlineauction.user.domain.service.UserService;
import com.onlineauction.user.domain.service.impl.UserServiceImpl;

/**
 * This class specifies domain service mappings.
 *  
 * @author hamo
 *
 */
public class DomainServiceModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(UserService.class).to(UserServiceImpl.class);
		bind(AuctionService.class).to(AuctionServiceImpl.class);
		bind(RecommendationService.class).to(RecommendationServiceImpl.class);
		bind(AuctionSearchService.class).to(AuctionSearchServiceImpl.class);
	}
}
