package com.example.demo.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalendarController {
	@GetMapping("/calendar")
	public String getCalender(Model model) {
		
		return "login/calendar";
	}

	@PostMapping("/calendar")
	public String postCalendar(Model model) {
		
		return "login/calendar";
	}
}


