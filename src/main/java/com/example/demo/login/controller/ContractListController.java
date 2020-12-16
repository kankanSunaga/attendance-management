package com.example.demo.login.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.model.WorkTimeForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.example.demo.login.domain.service.util.DateTimeUtil;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class ContractListController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	MonthService monthService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	UserIconService userIconService;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/contracts")
	public String getContractList(Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("contractList", contractService.selectMany(userId));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/contractList";
	}

	@GetMapping("/contract/{contractId}")
	public String getContractMonth(@ModelAttribute WorkTime workTime, Model model,
			@PathVariable("contractId") int contractId) throws IOException {

		int userId = sessionUtil.getUserId(request);

		List<Month> monthList = monthService.getMonthList(contractId);
		model.addAttribute("monthList", monthService.getMonthDate(monthList));
		model.addAttribute("officeName", contractService.activeSelectOne(contractId).getOfficeName());
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/contractMonth";
	}

	@GetMapping("/contract/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute WorkTimeForm form, Model model,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);

		LocalDate minWorkDay = dateTimeUtil.BeginningOfMonth(yearMonth);
		LocalDate maxWorkDay = minWorkDay.with(TemporalAdjusters.lastDayOfMonth());

		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);
		model.addAttribute("contract", workTimeService.setCalenderObject(calender, contractId, yearMonth));

		List<WorkTime> workTimes = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
		model.addAttribute("totalTime", workTimeService.samWorkTimeMinute(workTimes));

		model.addAttribute("contractDay", workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay));
		model.addAttribute("contractId", contractId);
		model.addAttribute("yearMonth", dateTimeUtil.toStringDate(minWorkDay, "yyyy年MM月"));
		model.addAttribute("yearMonthUrl", yearMonth);
		model.addAttribute("workTimes", workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay));
		model.addAttribute("displayStatus", contractService.selectDisplay(yearMonth, userId, contractId, LocalDate.now()));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("WorkTimeForm", contractService.setWorkTimeForm(form, userId));

		return "login/contractDay";
	}

	@PostMapping("/contract/{contractId}/{yearMonth}")
	public String postContractDay(@ModelAttribute WorkTime form, Model model,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);

		workTimeService.deleteOne(form.getWorkTimeId());
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "redirect:/contract/{contractId}/{yearMonth}";
	}

	@GetMapping("/contract/{contractId}/{yearMonth}/changeRequestStatus")
	public String changeRequestStatus(@PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth) {

		int userId = sessionUtil.getUserId(request);

		Month month = monthService.selectMonthTable(userId, contractId, yearMonth);
		monthService.update(monthService.changeRequest(month));

		return "redirect:/contract/{contractId}/{yearMonth}";
	}
}