package com.UBC417.A2.Helpers;

import com.UBC417.A2.Data.Seat;
import com.UBC417.A2.Data.SeatReservation;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;

public class Worker {

	public static void Process() 
	{
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		String Flight1, Flight1Seat, Flight2, Flight2Seat, Flight3, Flight3Seat, Flight4, Flight4Seat, FirstName, LastName;
		Iterable<Entity> reservations = SeatReservation.GetReservations();
		for(Entity reservation : reservations){
			Flight1 = (String) reservation.getProperty("Flight1");
			Flight1Seat = (String) reservation.getProperty("Flight1Seat");
			Flight2 = (String) reservation.getProperty("Flight2");
			Flight2Seat = (String) reservation.getProperty("Flight2Seat");
			Flight3 = (String) reservation.getProperty("Flight3");
			Flight3Seat = (String) reservation.getProperty("Flight3Seat");
			Flight4 = (String) reservation.getProperty("Flight4");
			Flight4Seat = (String) reservation.getProperty("Flight4Seat");
			FirstName = (String) reservation.getProperty("FirstName");
			LastName = (String) reservation.getProperty("LastName");
			try {
				if(Seat.ReserveSeats(Flight1, Flight1Seat, Flight2, Flight2Seat, Flight3, Flight3Seat, Flight4, Flight4Seat, FirstName, LastName)){
					ds.delete(reservation.getKey());
				}
			} catch (EntityNotFoundException e) {
				//I don't think anything needs to be done
				int temp = 1;
			}
		}
	}
}
