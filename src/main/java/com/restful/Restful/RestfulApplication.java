package com.restful.Restful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import paczuchaDatabase.DatabaseConnection;

/**
 * Contains main() function. Creates database connection. Runs our server application.
 */
@SpringBootApplication
public class RestfulApplication {

	public static void main(String[] args) {
		
		DatabaseConnection.createDatabaseConnection();
		
		SpringApplication.run(RestfulApplication.class, args);
		
		//DatabaseConnection.closeConnection();
	}

}
