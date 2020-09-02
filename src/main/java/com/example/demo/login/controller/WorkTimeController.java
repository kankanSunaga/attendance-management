package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class WorkTimeController {
	
	@GetMapping("/workTime")
	public String getWorkTime(Model model) {
		
		return "login/workTime";
	}

	@PostMapping("/workTime")
	public String postWorkTime(Model model) {
		
		return "login/login";
	}
}