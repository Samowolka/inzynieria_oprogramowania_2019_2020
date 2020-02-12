package paczuchaOffer;

import java.util.ArrayList;
import java.util.List;

import paczuchaDatabase.DatabaseConnection;

/**
 * Get, add and delete offers with this awesome offer manager!
 */
public class OfferManager {
	private List<Offer> offers;
	
	/**
	 * Imports list of offers from database.
	 */
	public OfferManager() {
		this.offers = DatabaseConnection.importOffers();
	}
	
	/**
	 * @return list of offers
	 */
	public List<Offer> getOffers() {
		return offers;
	}
	
	/**
	 * @return list of available offers
	 */
	public List<Offer> getAvailableOffers() {
		List<Offer> availableOffers = new ArrayList<Offer>();
		for(Offer o : offers) if(o.getIsAvailable()) availableOffers.add(o);
		return availableOffers;
	}
	
	/**
	 * Adds offer to offer list and to database.
	 * @param o - offer(OfferID, Manufacturer, Model, Type, DateOfProduction, Price, IsAvailable, NumberOfSeats, FuelConsumption)
	 * @return true, if operation was successful
	 */
	public boolean addOffer(Offer o) {
		if(DatabaseConnection.addOffer(o)) {
			return offers.add(o); 
		}
		return false;
	}
	
	/**
	 * Deletes offer from list and from database.
	 * @return true, if operation was successful
	 */
	public boolean deleteOffer(Offer o) { 
		if(DatabaseConnection.delete("Offers", o.getOfferID())) {
			return offers.remove(o); 
		}
		return false;
	}
	
	/**
	 * Deletes offer from offer list (by ID) and from database.
	 * @param id - offer ID
	 * @return true, if operation was successful
	 */
	public boolean deleteOffer(String id) {
		for( Offer o : offers ) {
			if(o.getOfferID().equals(id)) {
				if(DatabaseConnection.delete("Offers", id)) {
					if(offers.remove(o)) 	return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Get offer object by offer ID.
	 * @param offerID
	 */
	public Offer getOffer(String offerID) {
		for( Offer o : offers ) {
			if(o.getOfferID().equals(offerID)) return o;
		}
		return null;
	}
	
	/**
	 * Get string representation of all offers.
	 */
	public String toString() {
		String out="";
		for( Offer o : offers ) out += o.toString();
		return out;
	}
}
