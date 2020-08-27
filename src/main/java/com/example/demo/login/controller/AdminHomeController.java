package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminHomeController {
	
	@GetMapping("/admin-home")
	public String getAdminHome(Model model) {
		
		return "admin/AdminHome";
	}

	@PostMapping("/admin-home")
	public String postAdminHome(Model model) {
		
		return "admin/AdminHome";
	}
}
