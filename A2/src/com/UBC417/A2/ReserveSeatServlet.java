package com.UBC417.A2;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.UBC417.A2.Data.Seat;
import com.UBC417.A2.Data.SeatReservation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class ReserveSeatServlet extends HttpServlet {
	

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {

		// Get parameters
		String Flight1 = req.getParameter("Flight1");
		String Flight1Seat = req.getParameter("Flight1Seat");
		String Flight2 = req.getParameter("Flight2");		
		String Flight2Seat = req.getParameter("Flight2Seat");
		String Flight3 = req.getParameter("Flight3");		
		String Flight3Seat = req.getParameter("Flight3Seat");
		String Flight4 = req.getParameter("Flight4");		
		String Flight4Seat = req.getParameter("Flight4Seat");
		
		String FirstName = req.getParameter("FirstName");
		String LastName = req.getParameter("LastName");
		
		
		
		String forwardTo = "/seatConfirmation.jsp";
		try {
			
			if (!Seat.ReserveSeats(Flight1, Flight1Seat, Flight2, Flight2Seat, Flight3, Flight3Seat, Flight4, Flight4Seat, FirstName, LastName)) {
				// seat not reserved, show error page
				String waitList = req.getParameter("waitList");
				if(waitList.equals("on")){
					
					try {
						SeatReservation.CreateReservation(Flight1, Flight1Seat, Flight2, Flight2Seat, Flight3, Flight3Seat, Flight4, Flight4Seat, FirstName, LastName, true );
						forwardTo = "/reserveSeatWaiting.jsp";
					} catch (Exception e) {
						forwardTo = "/reserveSeatError.jsp";
					}
				} else{
					forwardTo = "/reserveSeatError.jsp";
				}
			}
		} catch (EntityNotFoundException e) {
			// seat not found, show error page
			forwardTo = "/reserveSeatError.jsp";
		}

		// redirect to final page
		ServletContext sc = getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(forwardTo);
		rd.forward(req, resp);
	}
}
