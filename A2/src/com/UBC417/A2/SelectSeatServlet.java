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
		
		
		for (int i = 1; i<5; i++) {
			String formattedFlight = String.format("Flight%d", i);
			String flight = req.getParameter(formattedFlight);
			if (!flight.equals("")) {
				Iterable<Entity> flightSeats = Seat.GetFreeSeats(flight);
				String formattedFlightSeats = String.format("flight%dSeats", i);
				req.setAttribute(formattedFlightSeats, flightSeats);
			}
		}
		
		
		//redirect to index.jsp
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/selectSeats.jsp");
		rd.forward(req, resp);
	}

}
