package paczuchaOffer;

import java.util.UUID;

/**
 * Offer class.
 * Contains offerID, manufacturer, model, type, dateOfProduction, price, 
 * information whether offer is available, numberOfSeats, fuelConsumption.
 */
public class Offer {
	
	private String offerID;
	private String manufacturer;
	private String model;
	private String type;
	private int dateOfProduction;
	private double price;
	private boolean isAvailable = true;
	private int numberOfSeats;
	private double fuelConsumption;

	/**
	 * Creates new offer. Generates offer ID. 
	 * @param offerID
	 * @param manufacturer
	 * @param model
	 * @param type
	 * @param dateOfProduction
	 * @param price
	 * @param numberOfSeats
	 * @param fuelConsumption
	 */
	public Offer(String manufacturer, String model, String type, int dateOfProduction, 
			double price, int numberOfSeats, double fuelConsumption) {
		
		this.offerID = UUID.randomUUID().toString();
		this.manufacturer = manufacturer;
		this.model = model;
		this.type = type;
		this.dateOfProduction = dateOfProduction;
		this.price = price;
		this.numberOfSeats = numberOfSeats;
		this.fuelConsumption = fuelConsumption;
	}
	
	/**
	 * Creates new offer. FOR DATABASE ONLY.
	 * @param offerID
	 * @param manufacturer
	 * @param model
	 * @param type
	 * @param dateOfProduction
	 * @param price
	 * @param isAvailable
	 * @param numberOfSeats
	 * @param fuelConsumption
	 */
	public Offer(String offerID, String manufacturer, String model, String type, int dateOfProduction, 
			double price, boolean isAvailable, int numberOfSeats, double fuelConsumption) {
		
		this.offerID = offerID;
		this.manufacturer = manufacturer;
		this.model = model;
		this.type = type;
		this.dateOfProduction = dateOfProduction;
		this.price = price;
		this.numberOfSeats = numberOfSeats;
		this.fuelConsumption = fuelConsumption;
	}

	public String getOfferID() 			{ return offerID; }
	public String getManufacturer() 	{ return manufacturer; }
	public String getModel() 			{ return model; }
	public String getType() 			{ return type; }
	public int getDateofProduction()	{ return dateOfProduction; }
	public double getPrice() 			{ return price; }
	public boolean getIsAvailable() 	{ return isAvailable; }
	public int getNumberOfSeats() 		{ return numberOfSeats; }
	public double getFuelConsumption() 	{ return fuelConsumption; }
	
	public void setDateOfProduction(int dateOfProduction) 	{ this.dateOfProduction = dateOfProduction; }
	public void setOfferID(String offerID) 					{ this.offerID = offerID; }
	public void setManufacturer(String manufacturer) 		{ this.manufacturer = manufacturer; }
	public void setModel(String model) 						{ this.model = model; }
	public void setType(String type) 						{ this.type = type; }
	public void setPrice(double price) 						{ this.price = price; }
	public void setNumberOfSeats(int numberOfSeats) 		{ this.numberOfSeats = numberOfSeats; }
	public void setFuelConsumption(double fuelConsumption) 	{ this.fuelConsumption = fuelConsumption; }
	public void setAvailable(boolean isAvailable) 			{ this.isAvailable = isAvailable; }
	
	/**
	 * Get string representation of an offer.
	 */
	@Override
	public String toString() {
		return "\nOffer {\n\tofferID: " + offerID + "\n\tmanufacturer: " + manufacturer + "\n\tmodel: " + model
				+ "\n\ttype: " + type + "\n\tdateOfProduction: " + dateOfProduction + "\n\tprice: " + price
				+ "\n\tisAvailable: " + isAvailable + "\n\tnumberOfSeats: " + numberOfSeats + "\n\tfuelConsumption: "
				+ fuelConsumption + "\n}";
	}
}
