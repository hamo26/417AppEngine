package com.onlineauction.auction.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class OnlineAuctionLoginFilter implements Filter {
	private static final Logger log = Logger
			.getLogger(OnlineAuctionLoginFilter.class.getName());
	
	@Override
	public void destroy() {
		// no-op
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) req;
		HttpServletResponse httpServletResponse = (HttpServletResponse) resp;
		
		log.info("Filtering uri request: " + httpRequest.getRequestURI());
		log.info("Session during filter is: " + httpRequest.getSession(false));
		
		//I expect this to short circuit if the http session is null.
		if (httpRequest.getRequestURI().startsWith("/login") || 
				null != httpRequest.getSession(false)
				&& null != (String) httpRequest.getSession(false).getAttribute("userName")) {
			log.info("continuing with chain");
			
			
			String queryString = httpRequest.getQueryString();
			if (queryString != null) {
				Map<String, String> queryMap = getQueryMap(queryString);
				req.setAttribute("queryMap", queryMap);
				
				log.info("The map contains: " + queryMap.toString());
			}
			
			chain.doFilter(req, resp);
		} else {
			log.info("redirecting request to login");
			httpServletResponse.sendRedirect("/loginAndRegistration/loginAndRegistrationForm.jsp");
		}
	}
	
	private static Map<String, String> getQueryMap(String query)  
	{  
	    String[] params = query.split("&");  
	    Map<String, String> map = new HashMap<String, String>();  
	    for (String param : params)  
	    {  
	        String name = param.split("=")[0];  
	        String value = param.split("=")[1];  
	        map.put(name, value);  
	    }  
	    return map;  
	}  
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// no-op
	}

}
