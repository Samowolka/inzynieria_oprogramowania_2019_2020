package paczuchaUser;


public class Employee extends User {
	private int bossID = 0;	
	
	public Employee(String username, String password) {
		super(username, password);
	}
	
	public Employee(String userID, String username, String password) {
		super(userID, username, password);
	}
	
	public Employee(String userID, String username, String password, String name, String surname) {
		super(userID, username, password);
	}
	
	public int getBossID() { return bossID; }
	public void setBossID(int bossID) { this.bossID = bossID; }
}
