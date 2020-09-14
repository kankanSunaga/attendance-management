package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String getHome(Model model) {
		
		return "login/home";
	}
	
	@PostMapping("/home")
	public String postHome(Model model) {
		
		return "login/home";
	}
}