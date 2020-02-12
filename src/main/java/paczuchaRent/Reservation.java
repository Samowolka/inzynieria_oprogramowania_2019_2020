package paczuchaRent;

import java.util.Date;
import java.util.UUID;

/**
 * Reservation class.
 * Contains reservationID, customerID, offerID, startDate, endDate.
 */
public class Reservation {

	private String reservationID;
	private String customerID;
	private String offerID;
	private Date startDate;
	private Date endDate;
	
	/**
	 * Creates new reservation. Generates reservation ID.
	 * @param customerID
	 * @param offerID
	 * @param startDate
	 * @param endDate
	 */
	public Reservation(String customerID, String offerID, Date startDate, Date endDate) {
		this.reservationID = UUID.randomUUID().toString();
		this.customerID = customerID;
		this.offerID = offerID;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	/**
	 * Creates new reservation. FOR DATABASE ONLY.
	 * @param customerID
	 * @param offerID
	 * @param startDate
	 * @param endDate
	 */
	public Reservation(String reservationID, String customerID, String offerID, Date startDate, Date endDate) {
		this.reservationID = reservationID;
		this.customerID = customerID;
		this.offerID = offerID;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getReservationID()	{ return reservationID; }
	public String getCustomerID() 		{ return customerID; }
	public String getOfferID() 			{ return offerID; }
	public Date getStartDate() 			{ return startDate; }
	public Date getEndDate() 			{ return endDate; }
	
	public void setReservationID(String reservationID) 	{ this.reservationID = reservationID; }
	public void setCustomerID(String customerID) 		{ this.customerID = customerID; }
	public void setOfferID(String offerID) 				{ this.offerID = offerID; }
	public void setStartDate(Date startDate) 			{ this.startDate = startDate; }
	public void setEndDate(Date endDate) 				{ this.endDate = endDate; }

	/**
	 * Get string representation of a reservation.
	 */
	@Override
	public String toString() {
		return "\nReservation {\n\treservationID: " + reservationID + "\n\tcustomerID: " + customerID + "\n\tofferID: "
				+ offerID + "\n\tstartDate: " + startDate + "\n\tendDate: " + endDate + "\n}";
	}
}
