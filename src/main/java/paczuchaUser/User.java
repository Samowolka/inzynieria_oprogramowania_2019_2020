package paczuchaUser;

import java.util.UUID;

/**
 * Abstract User class, inherited by 2 classes: Employee and Customer.
 * Contains userID, username and password.
 */
public abstract class User {
	private String userID;
	private String username;
	private String password;
	
	/**
	 * Abstract user constructor; inherited by 2 classes: Employee and Customer.
	 * ONLY FOR REGISTRATION. GENERATES USER ID.
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
		this.userID = UUID.randomUUID().toString();
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Abstract user constructor; inherited by 2 classes: Employee and Customer.
	 * ONLY FOR LOGGING IN.
	 * @param userID
	 * @param username
	 * @param password
	 */
	public User(String userID, String username, String password) {
		this.userID = userID;
		this.username = username;
		this.password = password;
	}

	public String getUserID() 	{ return userID; }
	public String getUsername() { return username; }
	public String getPassword() { return password; }
	
	public void setUsername(String u) { this.username = u; }
	public void setPassword(String p) { this.password = p; }

	/**
	 * Get string representation of an user.
	 */
	@Override
	public String toString() {
		return "\nUser {\n\tuserID: " + userID + "\n\tusername: " + username + "\n\tpassword: " + password + "\n}";
	}
}
