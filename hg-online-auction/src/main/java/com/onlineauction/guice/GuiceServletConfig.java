package com.onlineauction.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.onlineauction.guice.servicemappings.DomainServiceModule;
import com.onlineauction.guice.servletmapping.ServletMappingsModule;

/**
 * Guice configuration plugged into our web.xml. In a nut shell, this tells GAE to use
 * Guice for servlet mappings. All http request go through Guice and the bindings below.
 * 
 * @author hamo
 *
 */
public class GuiceServletConfig extends GuiceServletContextListener {

	@Override
	protected Injector getInjector() {
		return Guice.createInjector(new ServletMappingsModule(),
									new DomainServiceModule());
	}

}
