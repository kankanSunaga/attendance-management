package com.example.demo.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String getHome(Model model) {
		
		//現在の日付を取得
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatChange = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String formatChangedToday = today.format(formatChange);
		model.addAttribute("today", formatChangedToday);
		
		return "login/home";
	}
	
	@PostMapping("/home")
	public String postHome(Model model) {
		
		return "login/home";
	}
}