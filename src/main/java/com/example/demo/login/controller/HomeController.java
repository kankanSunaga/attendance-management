package com.example.demo.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.WorkTimeService;

@Controller
public class HomeController {

	@Autowired
	private WorkTimeService workTimeService;
	
	@GetMapping("/home")
	public String getHome(Model model) {
		
		//現在の日付を取得
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatChange = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String formatChangedToday = today.format(formatChange);
		model.addAttribute("today", formatChangedToday);
		
		model.addAttribute("contents","login/home :: home_contents");
		
		List<WorkTime> monthDataList= workTimeService.selectMonthData();
		
		model.addAttribute("monthDataList",monthDataList);
				
		System.out.println(monthDataList);
		
		List<String>  calendar = workTimeService.selectCalendar();
		
		model.addAttribute("calendar",calendar);
		
		return "login/headerLayout";
		
	}
	
	@PostMapping("/home")
	public String postHome(Model model) {
		
		return "login/home";
	}
}