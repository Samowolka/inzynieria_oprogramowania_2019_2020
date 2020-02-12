package paczuchaUser;

import java.util.Date;

public class Customer extends User {
	private String name;
	private String surname;
	private Date dateOfBirth;
	
	public Customer(String username, String password) {
		super(username, password);		
	}
	
	public Customer(String username, String password, String name, String surname, Date dateOfBirth) {
		super(username, password);
		this.name = name;
		this.surname = surname;
		this.dateOfBirth = dateOfBirth;
	}
	
	public Customer(String userID, String username, String password, String name, String surname, Date dateOfBirth) {
		super(userID, username, password);
	}
	
	public String getName() { return name; }
	public String getSurname() { return surname; }
	public Date getDateOfBirth() { return dateOfBirth; }
	
	public void setName(String name) { 
		this.name = name; 
	}
	public void setSurname(String surname) { 
		this.surname = surname; 
	}
	public void setDateOfBirth(Date dateOfBirth) { 
		this.dateOfBirth = dateOfBirth; 
	}
 }
