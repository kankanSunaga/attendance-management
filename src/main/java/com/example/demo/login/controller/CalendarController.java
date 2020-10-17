package com.example.demo.login.controller;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.WorkTimeService;

@Controller
public class CalendarController {
	
	@Autowired
	WorkTimeService workTimeService;
		
	@GetMapping("/calendar/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute WorkTime form, Model model, @PathVariable("contractId")int contractId, @PathVariable("yearMonth")String yearMonth, LocalDate minWorkDay, LocalDate maxWorkDay) {
		// yyyy-MM-01(月初のString)の作成
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(yearMonth);
		stringBuilder.insert(4, "-");
		stringBuilder.append("-01");
		String strMinWorkDay = stringBuilder.toString();
		
		// 引数のminWorkDayとmaxWorkDayの値を代入
		minWorkDay = LocalDate.parse(strMinWorkDay, DateTimeFormatter.ISO_DATE);
		maxWorkDay = minWorkDay.with(TemporalAdjusters.lastDayOfMonth());
		
		List<WorkTime> contractDayList = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
		model.addAttribute("contractDay", contractDayList);
		
		return "login/calendar";
	}
	
}


