package paczuchaRent;

import java.util.ArrayList;
import java.util.List;

import paczuchaDatabase.DatabaseConnection;
import paczuchaUser.UserManager;

/**
 * Get, add and delete reservations with this awesome reservation manager!
 */
public class ReservationManager{
	private List<Reservation> reservations;
	private List<Reservation> reservationArchives;

	/**
	 * Imports list of current reservations and list of all-time reservations from database.
	 */
	public ReservationManager() {
		this.reservations = DatabaseConnection.importReservations();
		this.reservationArchives = DatabaseConnection.importArchives();
	}
	
	/**
	 * @return list of current reservations
	 */
	public List<ReservationToFront> getReservations() { 
		String userID = UserManager.getLoggedUser();
		if(userID.equals("") ) return new ArrayList<ReservationToFront>();
		else {
			return DatabaseConnection.importUserReservations(userID);
		}
	}
	
	/**
	 * @return list of present and past reservations
	 */
	public List<Reservation> getReservationsFromArchives() { 
		reservationArchives = DatabaseConnection.importArchives();
		return reservationArchives;
	}
	
	/**
	 * Adds reservation to reservation lists and to database.
	 * @param r - reservation(reservationID, customerID, offerID, startDate, endDate)
	 * @return true, if operation was successful
	 */
	public boolean addReservation(Reservation r) 	{ 
		if(DatabaseConnection.addReservation(r)) {
			return reservations.add(r); 
		}
		return false;
	}
	
	/**
	 * Deletes reservation from current reservations list and from database.
	 * @return true, if operation was successful
	 */
	public boolean deleteReservation(Reservation r) { 
		if(DatabaseConnection.delete("Reservations", r.getReservationID())) {
			return reservations.remove(r);
		}
		return false;
	}
	
	/**
	 * Deletes reservation from current reservations list (by ID) and from database.
	 * Updates offer status (sets to being available).
	 * @param id - reservation ID
	 * @param offerID - offer ID
	 * @return true, if operation was successful
	 */
	public boolean deleteReservation(String id, String offerID) {
		for( Reservation r : reservations ) {
			if(r.getReservationID().equals(id)) {
				if(DatabaseConnection.delete("Reservations", id) && DatabaseConnection.updateOffer(offerID)) {
					if(reservations.remove(r)) 	return true;
				}	
			}
		}
		return false;
	}
	
	/**
	 * Get reservation object by reservation ID.
	 * @param reservationID
	 */
	public Reservation getReservation(String reservationID) {
		for( Reservation r : reservations ) {
			if(r.getReservationID().equals(reservationID)) return r;
		}
		return null;
	}
	
	/**
	 * Get string representation of all current reservations.
	 */
	public String toString() {
		String out="";
		for( Reservation r : reservations ) out += r.toString();
		return out;
	}
}
