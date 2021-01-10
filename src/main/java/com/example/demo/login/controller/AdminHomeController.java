package com.example.demo.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class AdminHomeController {

	@Autowired
	UserService userService;

	@Autowired
	MonthService monthService;
	
	@Autowired
	UserIconService userIconService;

	@GetMapping("/adminHome")
	public String getAdminHome(Model model) throws IOException {

		model.addAttribute("countPermission", userService.countPermission());
		model.addAttribute("ruquestUserCount", monthService.ruquestUserCount());
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "admin/adminHome";
	}

	@PostMapping("/adminHome")
	public String postAdminHome(Model model) {

	
		return "admin/adminHome";
	}
}
