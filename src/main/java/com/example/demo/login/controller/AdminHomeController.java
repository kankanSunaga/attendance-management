package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.UserService;

@Controller
public class AdminHomeController {
	
	@Autowired
	UserService userService; 
	
	@GetMapping("/admin-home")
	public String getAdminHome(Model model) {
		
		int countPermission = userService.countPermission();
		model.addAttribute("countPermission",countPermission);
		
		return "admin/adminHome";
	}

	@PostMapping("/admin-home")
	public String postAdminHome(Model model) {
		
		return "admin/adminHome";
	}
}
