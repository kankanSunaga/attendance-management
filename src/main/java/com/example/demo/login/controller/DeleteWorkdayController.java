package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DeleteWorkdayController {
	
	@GetMapping("/deleteWorkday")
	public String getDeleteWorkday() {
		
		return "login/deleteWorkday";
				
	}
	
	@PostMapping("/deleteWorkday")
	public String postDeleteWorkday() {
		
		return "login/deleteWorkday";
				
	}
}
