package com.example.demo.login.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CalendarController {
	@GetMapping("/calendar")
	public String getCalender(Model model) {
		
		return "login/calendar";
	}
}


