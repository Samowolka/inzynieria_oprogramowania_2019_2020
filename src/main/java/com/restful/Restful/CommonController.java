package com.restful.Restful;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Maps websites for login, registration and reservations.
 */
@Controller
public class CommonController {
	
	@GetMapping("/login")
	public String loginPage() {
		return "login";
	}
	
	@GetMapping("/register")
	public String registerPage() {
		return "register";
	}
	
	@GetMapping("/reservations")
	public String reservationPage() {
		return "reservations";
	}
}
