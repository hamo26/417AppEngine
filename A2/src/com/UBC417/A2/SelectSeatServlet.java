package com.UBC417.A2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UBC417.A2.Data.Seat;
import com.google.appengine.api.datastore.Entity;

@SuppressWarnings("serial")
public class SelectSeatServlet extends HttpServlet {
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		
		Set<String> flights = new HashSet<String>();
		
		for (int i = 1; i<5; i++) {
			String formatedFlight = String.format("Flight%d", i);
			String flight = req.getParameter(formatedFlight);
			if (flight != "") {
				flights.add(flight);
			}
		}
		
		req.setAttribute("flights", flights);
		
		for (String flight : flights) {
			Iterable<Entity> flightSeats = Seat.GetFreeSeats(flight);
			req.setAttribute(String.format("%sSeats", flight), flightSeats);
		}
		
		//redirect to index.jsp
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/selectSeats.jsp");
		rd.forward(req, resp);
	}

}
