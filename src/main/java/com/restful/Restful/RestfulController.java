package com.restful.Restful;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import paczuchaOffer.Offer;
import paczuchaOffer.OfferManager;
import paczuchaRent.ReservationToFront;
import paczuchaRent.ReservationManager;
import paczuchaRent.Reservation;
import paczuchaUser.Customer;
import paczuchaUser.User;
import paczuchaUser.UserManager;

/**
 * Control your Web application! Send and receive data in JSON format!
 * 
 * To test your application (and check data format) go to for example:
 * http://localhost:8080/getusers
 * http://localhost:8080/getuser?id=c581ded07-f3fe-41df-93c0-24ad88464a9c
 */
@RestController
public class RestfulController {

	private UserManager userManager;
	private OfferManager offerManager;
	private ReservationManager reservationManager;

	/**
	 * Create user, offer and reservation manager.
	 */
	@Autowired
	public RestfulController() {
		userManager = new UserManager();
		offerManager = new OfferManager();
		reservationManager = new ReservationManager();
	}

	/**
	 * Get all customers data in JSON format.
	 * @return list of customers
	 */
	@GetMapping("/getcustomers")
	public List<User> getCustomers() {
		return userManager.getCustomers();
	}

	/**
	 * Get all employees data in JSON format.
	 * @return list of employees
	 */
	@GetMapping("/getemployees")
	public List<User> getEmployees() {
		return userManager.getEmployees();
	}

	/**
	 * Get single user data in JSON format.
	 * @param id - user ID
	 * @return user
	 */
	@GetMapping("/getuser")
	public User getUser(@RequestParam(name = "id", required = true) String id) {
		return userManager.getUser(id);
	}

	/**
	 * Delete user (customers only).
	 * @param id - user ID
	 * @return user ID
	 */
	@DeleteMapping("/deleteuser")
	public String deleteUser(@RequestBody String id) {
		userManager.deleteUser(id);
		return id;
	}

	/**
	 * Create new customer.
	 * @param json - map of keys and values in JSON format {"username":"example", "password":"example", "name":"example", "surname":"example", "date":"example"}
	 * @return true, if creating new user was successful
	 */
	@RequestMapping(value = "/createuser", method = RequestMethod.POST)
	@ResponseBody
	public boolean createuser(@RequestBody Map<String, String> json) {
		Date d = null;
		try {
			d = new SimpleDateFormat("yyyy-MM-dd").parse(json.get("birth"));
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
		Customer u = new Customer(json.get("username"), json.get("password"), json.get("name"), json.get("surname"), d);
		return userManager.registration(u);
	}

	/**
	 * Log in.
	 * @param username
	 * @param password
	 * @return 0, if customer is logged in; 1, if employee is logged in; -1 if user does not exist
	 */
	@GetMapping("/loginsite")
	public int login(@RequestParam(name = "login", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {
		return userManager.login(username, password);
	}

	/**
	 * Change user username.
	 * @param username - new username
	 * @param id - user ID
	 * @return user
	 */
	@PutMapping("/changeusername")
	public User changeUsername(@RequestBody String username, String id) {
		User u = userManager.getUser(id);
		u.setUsername(username);
		return u;
	}

	/**
	 * Log out.
	 * @return true, if user managed to log out
	 */
	@GetMapping("/logout")
	public boolean logout() {
		return UserManager.logout();
	}

	/**
	 * Get offer in JSON format.
	 * @param id - offer ID
	 * @return offer
	 */
	@GetMapping("/getoffer")
	public Offer getOffer(@RequestParam(name = "id", required = true) String id) {
		return offerManager.getOffer(id);
	}

	/**
	 * Get all offers data in JSON format.
	 * @return list of all offers
	 */
	@GetMapping("/getoffers")
	public List<Offer> getOffers() {
		System.out.println(offerManager.getOffers());
		return offerManager.getOffers();
	}

	/**
	 * Get available offers data in JSON format.
	 * @return list of available offers
	 */
	@GetMapping("/getavailableoffers")
	public List<Offer> getAvailableOffers() {
		System.out.println(offerManager.getAvailableOffers());
		return offerManager.getAvailableOffers();
	}

	/**
	 * Delete offer.
	 * @param id - offer ID
	 * @return true, if offer was successfully deleted
	 */
	@DeleteMapping("/deleteoffer")
	public boolean deleteOffer(@RequestBody String id) {
		return offerManager.deleteOffer(id);
	}

	/**
	 * Create new offer.
	 * @param json - map of keys and values in JSON format {"manufacturer":"", "model":"", "type":"", "dateOfProduction":"", "price":"", "numberOfSeats":"", "fuelConsumption":""}
	 * @return true, if creating new offer was successful
	 */
	@RequestMapping(value = "/createoffer", method = RequestMethod.POST)
	@ResponseBody
	public boolean createOffer(@RequestBody Map<String, String> json) {
		Offer o = new Offer(json.get("manufacturer"), json.get("model"), json.get("type"),
				Integer.parseInt(json.get("dateOfProduction")), Double.parseDouble(json.get("price")),
				Integer.parseInt(json.get("numberOfSeats")), Double.parseDouble(json.get("fuelConsumption")));
		return offerManager.addOffer(o);
	}

	/**
	 * Get current reservations data in JSON format.
	 * @return list of current reservations
	 */
	@GetMapping("/getreservations")
	@ResponseBody
	public List<ReservationToFront> getReservations() {
		return reservationManager.getReservations();
	}

	/**
	 * Create new reservation.
	 * @param offerID
	 * @return true, if creating new reservation was successful
	 */
	@GetMapping("/createreservation")
	@ResponseBody
	public boolean createReservation(String offerID) {
		Date startDate = Calendar.getInstance().getTime();

		Calendar endCalendar = Calendar.getInstance();
		endCalendar.add(Calendar.MONTH, 1);
		Date endDate = endCalendar.getTime();

		Reservation r = new Reservation(UserManager.getLoggedUser(), offerID, startDate, endDate);

		Offer o = offerManager.getOffer(offerID);
		if (o == null)
			return false;
		o.setAvailable(false);
		return reservationManager.addReservation(r);
	}

	/**
	 * Delete reservation.
	 * @param id - reservation ID
	 * @return true, if reservation was successfully deleted
	 */
	@DeleteMapping("/deletereservation")
	public boolean deleteReservation(@RequestBody String id) {
		Reservation r = reservationManager.getReservation(id);
		Offer o = offerManager.getOffer(r.getOfferID());
		if (o == null)
			return false;
		o.setAvailable(true);

		return reservationManager.deleteReservation(id, r.getOfferID());
	}
	
	/**
	 * Get all reservations data from archives in JSON format.
	 * @return list of all present and past reservations
	 */
	@GetMapping("/getarchive")
	public List<Reservation> getRentalArchives() {
		return reservationManager.getReservationsFromArchives();
	}
}
