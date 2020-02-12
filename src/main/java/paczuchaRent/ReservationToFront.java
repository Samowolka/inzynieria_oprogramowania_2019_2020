package paczuchaRent;

import java.util.Date;

/**
 * ReservationToFront class. ONLY FOR RESERVATION WEB VIEW.
 * Contains reservationID, manufacturer, model, price, startDate and endDate.
 */
public class ReservationToFront {
	
	private String reservationID;
	private String manufacturer;
	private String model;
	private double price;
	private Date startDate;
	private Date endDate;
	
	/**
	 * Creates new reservation. ONLY FOR RESERVATION WEB VIEW.
	 * @param rentalID
	 * @param manufacturer
	 * @param model
	 * @param price
	 * @param startDate
	 * @param endDate
	 */
	public ReservationToFront(String rentalID, String manufacturer, String model, double price, Date startDate, Date endDate) {
		this.reservationID = rentalID;
		this.manufacturer = manufacturer;
		this.model = model;
		this.price = price;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getRentalID() 		{ return reservationID; }
	public String getManufacturer() 	{ return manufacturer; }
	public String getModel() 			{ return model; }
	public double getPrice() 			{ return price; }
	public Date getStartDate() 			{ return startDate; }
	public Date getEndDate() 			{ return endDate; }

	public void setRentalID(String reservationID) 		{ this.reservationID = reservationID; }
	public void setManufacturer(String manufacturer) 	{ this.manufacturer = manufacturer; }
	public void setModel(String model) 					{ this.model = model; }
	public void setPrice(double price) 					{ this.price = price; }
	public void setStartDate(Date startDate) 			{ this.startDate = startDate; }
	public void setEndDate(Date endDate) 				{ this.endDate = endDate; }
}
