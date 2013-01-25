package com.UBC417.A1.Helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UBC417.A1.Data.Flight;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

@SuppressWarnings("serial")
public class FreeSeats extends HttpServlet {
	static Filter personSittingFilter = new FilterPredicate("PersonSitting", FilterOperator.NOT_EQUAL, null);
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		// Get all seats that are not free
		Iterable<Entity> flights = Flight.GetFlights();
		for(Entity flight : flights){
			Query q = new Query(KeyFactory.keyToString(flight.getKey())).setFilter(personSittingFilter);

			List<Entity> l = new ArrayList<Entity>();
			Iterable<Entity> seats = ds.prepare(q).asIterable();
			for (Entity s : seats) {
				// free seat
				s.setProperty("PersonSitting", null);
				l.add(s);
			}
	
			// put seats back into datastore
			ds.put(l);
		}

		// redirect to freeSeats.jsp
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/FreeSeats.jsp");
		rd.forward(req, resp);
	}
}
