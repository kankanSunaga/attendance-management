package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignupController {
	
	@GetMapping("/signup")
	public String getSignup(Model model) {
		
		return "login/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(Model model) {
		
		return "login/unapproved";
	}
}