package com.onlineauction.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.onlineauction.guice.servicemappings.DomainServiceModule;
import com.onlineauction.guice.servletmapping.ServletMappingsModule;

public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletMappingsModule(),
									new DomainServiceModule());
	}

}
