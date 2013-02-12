package com.UBC417.A2.Data;

import java.util.ConcurrentModificationException;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Transaction;
import com.google.appengine.api.datastore.TransactionOptions;

//Helper class for flight seats.
public class Seat {

	// Create a seat on a specific flight,
	// @store = true, when you want to commit entity to the datastore
	// = false, when you want to commit entity later, like in a batch operation
	public static Entity CreateSeat(String SeatID, String FlightName,
			boolean store) {
		Entity e = new Entity(FlightName, SeatID);
		e.setProperty("PersonSitting", null);

		if (store) {
			DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
			ds.put(e);
		}

		return e;
	}

	// Frees specific seat(SeatID) on flight(FlightKey)
	public static void FreeSeat(String SeatID, String FlightName) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		try {
			Entity e = ds.get(KeyFactory.createKey(FlightName, SeatID));

			e.setProperty("PersonSitting", null);
			ds.put(e);
		} catch (EntityNotFoundException e) {
		}
	}

	// Returns all free seats on a specific flight(FlightKey)
	public static Iterable<Entity> GetFreeSeats(String FlightName) {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query(FlightName).addFilter("PersonSitting",
				FilterOperator.EQUAL, null);
		return ds.prepare(q).asIterable();
	}

	// Reserves a specific seat(SeatID) on a specific flight(FlightKey)
	public static boolean ReserveSeat(String FlightName, String SeatID,
			String FirstName, String LastName) throws EntityNotFoundException {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();

		for (int i = 0; i < 10; i++) {
			Transaction tx = ds.beginTransaction();
			try {
				Entity e = ds.get(tx, KeyFactory.createKey(FlightName, SeatID));

				if (e.getProperty("PersonSitting") != null)
					return false;

				e.setProperty("PersonSitting", FirstName + " " + LastName);
				ds.put(tx, e);
				tx.commit();
				return true;
			} catch (ConcurrentModificationException e) {
				// continue
			} finally {
				if (tx.isActive())
					tx.rollback();
			}
		}
		throw new ConcurrentModificationException();
	}

	public static boolean ReserveSeats(String Flight1, String Flight1Seat,
			String Flight2, String Flight2Seat, String Flight3,
			String Flight3Seat, String Flight4, String Flight4Seat,
			String FirstName, String LastName) throws EntityNotFoundException {
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		TransactionOptions options = TransactionOptions.Builder.withXG(true);
		for (int i = 0; i < 10; i++) {
			Transaction tx = ds.beginTransaction(options);
			try {
				if(Flight1 != null && Flight1Seat != null){
					Entity e1 = ds.get(tx, KeyFactory.createKey(Flight1, Flight1Seat));
					if (e1.getProperty("PersonSitting") != null){
						return false;
					}
					e1.setProperty("PersonSitting", FirstName + " " + LastName);
					ds.put(tx, e1);
				}
				if(Flight2 != null && Flight2Seat != null){
					Entity e2 = ds.get(tx, KeyFactory.createKey(Flight2, Flight2Seat));
					if (e2.getProperty("PersonSitting") != null){
						return false;
					}
					e2.setProperty("PersonSitting", FirstName + " " + LastName);
					ds.put(tx, e2);
				}
				if(Flight3 != null && Flight3Seat != null){
					Entity e3 = ds.get(tx, KeyFactory.createKey(Flight3, Flight3Seat));
					if (e3.getProperty("PersonSitting") != null){
						return false;
					}
					e3.setProperty("PersonSitting", FirstName + " " + LastName);
					ds.put(tx, e3);
				}
				if(Flight4 != null && Flight4Seat != null){
					Entity e4 = ds.get(tx, KeyFactory.createKey(Flight4, Flight4Seat));
					if (e4.getProperty("PersonSitting") != null){
						return false;
					}
					e4.setProperty("PersonSitting", FirstName + " " + LastName);
					ds.put(tx, e4);
				}
				tx.commit();
				return true;
			} catch (ConcurrentModificationException e) {
				// continue
			} finally {
				if (tx.isActive())
					tx.rollback();
			}
		}
		throw new ConcurrentModificationException();
	}
}
