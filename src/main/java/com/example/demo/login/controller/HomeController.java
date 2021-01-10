package com.example.demo.login.controller;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.DayOfWeekService;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.example.demo.login.domain.service.util.DateTimeUtil;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class HomeController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	DayOfWeekService dayOfWeekService;

	@Autowired
	MonthService monthService;

	@Autowired
	UserIconService userIconService;

	@Autowired
	SessionUtil sessionUtil;
	
	@Autowired
	DateTimeUtil dateTimeUtil;

	@GetMapping("/home")
	public String getHome(Model model, HttpServletRequest request) throws IOException {

		int userId = sessionUtil.getUserId(request);
		int contractId = contractService.underContract(userId, LocalDate.now()).getContractId();

		String yearMonth = dateTimeUtil.toStringDate(LocalDate.now(), "yyyyMM");
		int monthId = monthService.selectMonthTable(1, yearMonth).getMonthId();

		int contractTime = contractService.underContract(userId, LocalDate.now()).getContractTime();
		int totalTime = monthService.getTotalTime(monthService.getWorkTime(contractId, monthId));
		
		boolean deadlineStatus = monthService.selectMonthTable(contractId, yearMonth).isDeadlineStatus();
		LocalDate lastWeekDay = dayOfWeekService.getLastWeekDay(LocalDate.now());

		model.addAttribute("nowDate", dateTimeUtil.toStringDate(LocalDate.now(), "yyyy年MM月dd日(E)"));
		model.addAttribute("officeName", contractService.underContract(userId, LocalDate.now()).getOfficeName());
		model.addAttribute("quota", monthService.checkQuota(contractTime, totalTime));
		model.addAttribute("execution", dateTimeUtil.getTotalTime(totalTime));
		model.addAttribute("deadline", monthService.deadlineCheck(deadlineStatus, lastWeekDay, LocalDate.now()));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/home";
	}
	
	@GetMapping("/deadline")
	public String deadline(Model model, HttpServletRequest request) throws IOException {

		int userId = sessionUtil.getUserId(request);
		int contractId = contractService.underContract(userId, LocalDate.now()).getContractId();
		String yearMonth = dateTimeUtil.toStringDate(LocalDate.now(), "yyyyMM");

		Month month = monthService.selectMonthTable(contractId, yearMonth);
		monthService.update(monthService.deadlineStatus(month));
		
		return "redirect:/home";
	}
}