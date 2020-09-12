package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HamburgerMenuController {
	
	@GetMapping("/changePassword")
	public String getChangePassword(Model model) {
				
		return "login/changePassword";
	}
	
	@GetMapping("/changeEmail")
	public String getChangeEmail(Model model) {
				
		return "login/changeEmail";
	}
}