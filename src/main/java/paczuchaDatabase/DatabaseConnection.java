package paczuchaDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import paczuchaOffer.Offer;
import paczuchaRent.ReservationToFront;
import paczuchaRent.Reservation;
import paczuchaUser.Customer;
import paczuchaUser.Employee;
import paczuchaUser.User;

/**
 * Manage your data with one and only Database Connection! 
 * Add customers, offers and reservations in no time! For free!
 */
public class DatabaseConnection {
	static Connection connection = null;

	/**
	 * Create connection with Microsoft SQL Database.
	 */
	public static void createDatabaseConnection() {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			connection = DriverManager.getConnection(
					"jdbc:sqlserver://127.0.0.1:1433;\" + \"databaseName=Restful;integratedSecurity=true;");
		} catch (Exception e) {
			System.out.println("Somethin' went wrong while connecting to database: " + e);
		}
	}

	/**
	 * Add new customer to database.
	 * 
	 * @param c - Customer(CustomerID, Username, Password, Name, Surname, DateOfBirth)
	 * @return true, if adding new customer to database was successful.
	 */
	public static boolean addCustomer(Customer c) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String stringDate = dateFormat.format(c.getDateOfBirth());

			Statement statement = null;
			String query = "INSERT INTO Restful.dbo.Customers(CustomerID, Username, Password, Name, Surname, DateOfBirth) VALUES "
					+ "('" + c.getUserID() + "','" + c.getUsername() + "','" + c.getPassword() + "','" + c.getName()
					+ "','" + c.getSurname() + "','" + stringDate + "')";

			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Creating 'New Customer' query completed.");

			if (statement != null) {
				statement.close();
			}
			return true;

		} catch (SQLException e) {
			System.out.println("Problem with creating 'New Customer' query. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Add new offer to database.
	 * 
	 * @param o - Offer(OfferID, Manufacturer, Model, Type, DateOfProduction, Price, IsAvailable, NumberOfSeats, FuelConsumption)
	 * @return true, if adding new offer to database was successful.
	 */
	public static boolean addOffer(Offer o) {
		int isAvailable = 0;
		if (o.getIsAvailable() == true)
			isAvailable = 1;
		try {
			Statement statement = null;
			String query = "INSERT INTO Restful.dbo.Offers(OfferID, Manufacturer, Model, Type, DateOfProduction, Price, IsAvailable, NumberOfSeats, FuelConsumption) VALUES "
					+ "('" + o.getOfferID() + "','" + o.getManufacturer() + "','" + o.getModel() + "','" + o.getType()
					+ "','" + o.getDateofProduction() + "','" + o.getPrice() + "','" + isAvailable + "','"
					+ o.getNumberOfSeats() + "','" + o.getFuelConsumption() + "')";

			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Creating 'New Offer' query completed.");

			if (statement != null) {
				statement.close();
			}

			return true;

		} catch (SQLException e) {
			System.out.println("Problem with creating 'New Offer' query. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Add reservation to database (to Reservation and Archive table).
	 * 
	 * @param r - Reservation(ReservationID, CustomerID, OfferID, StartDate, EndDate)
	 * @return true, if adding new reservation to database was successful.
	 */
	public static boolean addReservation(Reservation r) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String stringStartDate = dateFormat.format(r.getStartDate());
			String stringEndDate = dateFormat.format(r.getEndDate());

			Statement statement = null;
			String query = "INSERT INTO Restful.dbo.Reservations(ReservationID, CustomerID, OfferID, StartDate, EndDate) VALUES "
					+ "('" + r.getReservationID() + "','" + r.getCustomerID() + "','" + r.getOfferID() + "','"
					+ stringStartDate + "','" + stringEndDate + "')";

			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Creating 'New Reservation' query completed.");

			if (statement != null) {
				statement.close();
			}

			statement = null;
			query = "INSERT INTO Restful.dbo.Archive(ReservationID, CustomerID, OfferID, StartDate, EndDate) VALUES " + "('"
					+ r.getReservationID() + "','" + r.getCustomerID() + "','" + r.getOfferID() + "','" + stringStartDate
					+ "','" + stringEndDate + "')";

			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Adding 'New Reservation' to archive query completed.");

			if (statement != null) {
				statement.close();
			}

			statement = null;
			query = "UPDATE Restful.dbo.Offers set IsAvailable = 0 WHERE OfferID = '" + r.getOfferID() + "'";

			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Updating offer availability (0) query completed.");

			if (statement != null) {
				statement.close();
			}

			return true;

		} catch (SQLException e) {
			System.out.println("Problem with creating 'New Reservation' query. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Delete record from database.
	 * 
	 * @param table - table name (available tables: Customers, Offers, Reservations)
	 * @param ID    - object ID (CustomerID, OfferID, Reservation)
	 */
	public static boolean delete(String table, String objectID) {
		String ID = null;
		if (table.equals("Customers"))
			ID = "CustomerID";
		else if (table.equals("Offers"))
			ID = "OfferID";
		else if (table.equals("Reservations"))
			ID = "ReservationID";
		try {
			Statement statement = null;
			String query = "DELETE from Restful.dbo." + table + " WHERE " + ID + " = '" + objectID + "'";
			statement = connection.createStatement();
			statement.executeUpdate(query);
			System.out.println("Delete object (ID: " + objectID + ") from " + table + " query completed.");

			if (statement != null) {
				statement.close();
			}
			return true;

		} catch (SQLException e) {
			System.out.println(
					"Problem deleting object (ID: " + objectID + ") from " + table + " table. " + e.getMessage());
			return false;
		}
	}

	/**
	 * Updates offer record in database. Sets availability status to 1, so the offer
	 * is now available for reservation.
	 * 
	 * @param offerID
	 * @return true, if updating offer was successful (IsAvailable parameter should be set to 1).
	 */
	public static boolean updateOffer(String offerID) {
		try {
			Statement stmt = null;
			String query = "UPDATE Restful.dbo.Offers SET IsAvailable = 1 WHERE OfferID = '" + offerID + "'";
			stmt = connection.createStatement();
			stmt.executeUpdate(query);
			System.out.println("Updating offer availability (1) query completed.");

			if (stmt != null) {
				stmt.close();
			}

			return true;

		} catch (SQLException e) {
			System.out.println("Problem updating offer (ID: " + offerID + "). " + e.getMessage());
			return false;
		}
	}

	/**
	 * Import customers from database.
	 * @return list of customers(CustomerID, Username, Password, Name, Surname, DateOfBirth)
	 */
	public static List<User> importCustomers() {
		try {
			List<User> customers = new ArrayList<User>();

			Statement statement = null;
			String query = "SELECT * FROM Restful.dbo.Customers";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				// if (CustomerID.equals(rs.getString("CustomerID"))) found = true;
				Customer c = new Customer(rs.getString("CustomerID"), rs.getString("Username"),
						rs.getString("Password"), rs.getString("Name"), rs.getString("Surname"),
						new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("DateOfBirth")));

				customers.add(c);
			}

			if (statement != null) {
				statement.close();
			}

			return customers;

		} catch (SQLException e) {
			System.out.println("Problem encountered while importing customers. " + e.getMessage());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Date parsing failed.");
		}

		return new ArrayList<User>();
	}

	/**
	 * Import employees from database.
	 * @return list of employees(EmployeeID, Username, Password, Name, Surname)
	 */
	public static List<User> importEmployees() {
		try {
			List<User> employees = new ArrayList<User>();

			Statement statement = null;
			String query = "SELECT * FROM Restful.dbo.Employees";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				// if (CustomerID.equals(rs.getString("CustomerID"))) found = true;
				Employee c = new Employee(rs.getString("EmployeeID"), rs.getString("Username"),
						rs.getString("Password"), rs.getString("Name"), rs.getString("Surname"));

				employees.add(c);
			}

			if (statement != null) {
				statement.close();
			}

			return employees;

		} catch (SQLException e) {
			System.out.println("Problem encountered while importing employees. " + e.getMessage());
		}

		return new ArrayList<User>();
	}

	/**
	 * Import offers from database.
	 * @return list of offers(OfferID, Manufacturer, Model, Type, DateOfProduction, Price, IsAvailable, NumberOfSeats, FuelConsumption)
	 */
	public static List<Offer> importOffers() {
		try {
			List<Offer> offers = new ArrayList<Offer>();

			Statement statement = null;
			String query = "SELECT * FROM Restful.dbo.Offers";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Offer o = new Offer(rs.getString("OfferID"), rs.getString("Manufacturer"), rs.getString("Model"),
						rs.getString("Type"), rs.getInt("DateOfProduction"), rs.getDouble("Price"),
						rs.getBoolean("IsAvailable"), rs.getInt("NumberOfSeats"), rs.getDouble("FuelConsumption"));

				offers.add(o);
			}
			if (statement != null) {
				statement.close();
			}

			return offers;

		} catch (SQLException e) {
			System.out.println("Problem encountered while importing offers. " + e.getMessage());
		}
		return new ArrayList<Offer>();
	}

	/**
	 * Import reservations from database.
	 * @return list of reservations(ReservationID, CustomerID, OfferID, StartDate, EndDate)
	 */
	public static List<Reservation> importReservations() {
		try {
			List<Reservation> reservations = new ArrayList<Reservation>();

			Statement statement = null;
			String query = "SELECT * FROM Restful.dbo.Reservations";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Reservation r = new Reservation(rs.getString("ReservationID"), rs.getString("CustomerID"),
						rs.getString("OfferID"), new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("StartDate")),
						new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("EndDate")));

				reservations.add(r);
			}
			if (statement != null) {
				statement.close();
			}

			return reservations;

		} catch (SQLException e) {
			System.out.println("Problem encountered while importing reservations. " + e.getMessage());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Date parsing failed.");
		}
		return new ArrayList<Reservation>();
	}

	/**
	 * Import all reservations (present and past) from database archives.
	 * @return list of reservations(ReservationID, CustomerID, OfferID, StartDate, EndDate)
	 */
	public static List<Reservation> importArchives() {
		try {
			List<Reservation> reservations = new ArrayList<Reservation>();

			Statement statement = null;
			String query = "SELECT * FROM Restful.dbo.Archive";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				Reservation r = new Reservation(rs.getString("ReservationID"), rs.getString("CustomerID"),
						rs.getString("OfferID"), new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("StartDate")),
						new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("EndDate")));

				reservations.add(r);
			}
			if (statement != null) {
				statement.close();
			}

			return reservations;

		} catch (SQLException e) {
			System.out.println("Impossible. Perhaps the archives are incomplete.");
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Date parsing failed.");
		}
		return new ArrayList<Reservation>();
	}

	/**
	 * Get user's current reservations.
	 * @param loggedUser - logged user ID
	 * @return list of user current reservations(ReservationID, Manufacturer, Model, Price, StartDate, EndDate)
	 */
	public static List<ReservationToFront> importUserReservations(String loggedUser) {
		try {
			List<ReservationToFront> reservations = new ArrayList<ReservationToFront>();

			Statement statement = null;
			String query = "SELECT r.ReservationID," + " o.Manufacturer," + " o.Model," + " o.Price,"
					+ " r.StartDate," + " r.EndDate" + " FROM Restful.dbo.Reservations r"
					+ " INNER JOIN Restful.dbo.Offers o" + " ON r.OfferID = o.OfferID WHERE CustomerID = '" + loggedUser
					+ "'";

			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()) {
				ReservationToFront r = new ReservationToFront(rs.getString("ReservationID"), rs.getString("Manufacturer"),
						rs.getString("Model"), rs.getInt("Price"),
						new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("StartDate")),
						new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("EndDate")));

				reservations.add(r);
			}
			if (statement != null) {
				statement.close();
			}

			return reservations;

		} catch (SQLException e) {
			System.out.println("Problem encountered while importing user's (ID: " + loggedUser + ") list of reservations. " + e.getMessage());
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			System.out.println("Date parsing failed.");
		}
		return new ArrayList<ReservationToFront>();
	}

	/**
	 * Close connection with database.
	 */
	public static void closeConnection() {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}