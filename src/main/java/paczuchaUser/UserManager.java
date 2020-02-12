package paczuchaUser;

import java.util.List;

import paczuchaDatabase.DatabaseConnection;

/**
 * Get, add and delete users with this awesome user manager!
 */
public class UserManager {
	private List<User> customers;
	private List<User> employees;
	private static String loggedUser = "";
	
	/**
	 * @return logged user ID
	 */
	public static String getLoggedUser() { return loggedUser; }
	
	/**
	 * Sets loggedUser parameter to logged user ID, so the application knows which user is logged in.
	 * @param loggedUser - user ID
	 */
	public static void setLoggedUser(String loggedUser) { UserManager.loggedUser = loggedUser; }

	/**
	 * Creates UserManager object and initializes list of users.
	 */
	public UserManager() {
		this.customers = DatabaseConnection.importCustomers();
		this.employees = DatabaseConnection.importEmployees();
	}
	
	/**
	 * Process of logging in. Checks if our user exists in a database. 
	 * Then checks if given password is correct.
	 * @param username 
	 * @param password
	 * @return 0, if customer is logged in; 1, if employee is logged in; -1 if user does not exist.
	 */
	public int login(String username, String password) {
		for( User u : customers ) {
			if (u.getUsername().equals(username) && u.getPassword().contentEquals(password) ) {
					UserManager.setLoggedUser(u.getUserID());
					return 0;
			}
		}
		
		for( User u : employees ) {
			if (u.getUsername().equals(username) && u.getPassword().contentEquals(password) ) {
					UserManager.setLoggedUser(u.getUserID());
					return 1;
			}
		}
		System.out.println("User " + username +  " does not exist.\n");
		UserManager.setLoggedUser("");
		return -1;
	}
	
	/**
	 * Process of registration. Checks if user with given name already exists.
	 * If not - we create new Customer.
	 * IMPORTANT: with this method we can only create Customer objects (not Employees), for security reasons.
	 * To create Employee in database we have to do it outside the program, changing database manually.
	 * @param username
	 * @param password
	 */
	public boolean registration(Customer user) {
		boolean exists = false;
		
		for( User u: customers ) {
			if( u.getUsername().equals(user.getUsername()) )  exists = true;
		}
		
		for( User u: employees ) {
			if( u.getUsername().equals(user.getUsername()) )  exists = true;
		}
		
		if(exists) System.out.println("This username is already in use.\n");
		else {
			
			if(DatabaseConnection.addCustomer(user)) {
				String message = "Congratulations " + user.getUsername() + ". You have created a customer account.\n";
				System.out.println(message);
				customers.add(user);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Sets global parameter 'loggedUser' to none ("").
	 */
	public static boolean logout() {
		UserManager.setLoggedUser("");
		return true;
	}
	
	/**
	 * Deletes user (customers only) from list and from database.
	 * @return true, if operation was successful
	 */
	public boolean deleteUser(User u) {
		if(DatabaseConnection.delete("Customers", u.getUserID())) {
			if(customers.remove(u)) 	return true;
		}
		return false;
	}
	
	/**
	 * Delete user (customers only) by user ID from list and from database.
	 * @param id - user ID (customer ID)
	 * @return true, if operation was successful
	 */
	public boolean deleteUser(String id) {
		for( User u : customers ) {
			if(u.getUserID().equals(id)) {
				if(DatabaseConnection.delete("Customers", id)) {
					if(customers.remove(u)) 	return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return list of customers
	 */
	public List<User> getCustomers() { return this.customers; }
	
	/**
	 * @return list of employees
	 */
	public List<User> getEmployees() { return this.employees; }
	
	/**
	 * Get user by userID.
	 * @param userID
	 * @return user, if exists; if not then null
	 */
	public User getUser(String userID) {
		for( User u : customers ) {
			if(u.getUserID().equals(userID)) return u;
		}
		
		for( User u : employees ) {
			if(u.getUserID().equals(userID)) return u;
		}
		return null;
	}
	
	/**
	 * Get user by username and password.
	 * @param username
	 * @param password
	 * @return user, if exists; if not then null
	 */
	public User getUser(String username, String password) {
		for( User u : customers ) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) return u;
		}
		
		for( User u : employees ) {
			if(u.getUsername().equals(username) && u.getPassword().equals(password)) return u;
		}
		return null;
	}

	/**
	 * Get string representation of all users.
	 */
	public String toString() {
		String out="";
		for( User u : customers ) out += u.toString();
		for( User u : employees ) out += u.toString();
		return out;
	}	
}
