package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HamburgerMenuController {
	
	@GetMapping("/changePassword")
	public String getChangePassword(Model model) {
		model.addAttribute("contents","login/changePassword :: changePassword_contents");
		
		return "login/headerLayout";
	}
	
	@GetMapping("/changeEmail")
	public String getChangeEmail(Model model) {
		model.addAttribute("contents","login/changeEmail :: changeEmail_contents");
		
		return "login/headerLayout";
	}
}