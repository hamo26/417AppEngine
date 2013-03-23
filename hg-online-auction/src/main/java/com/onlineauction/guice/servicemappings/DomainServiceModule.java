package com.onlineauction.guice.servicemappings;

import com.google.inject.AbstractModule;
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
	}

}
