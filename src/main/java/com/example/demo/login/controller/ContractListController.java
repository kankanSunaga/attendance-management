package com.example.demo.login.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/contractMonth";
	}

	@GetMapping("/contract/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute WorkTime workTime, WorkTimeForm form, Model model,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);
		int monthId = monthService.selectMonthTable(contractId, yearMonth).getMonthId();

		LocalDate minWorkDay = dateTimeUtil.BeginningOfMonth(yearMonth);
		List<WorkTime> workTimeMonth = monthService.getMonth(monthId);
		List<Map<String, Object>> workTimeList = workTimeService.changeTimeFormat(workTimeMonth);
		
		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);

		model.addAttribute("contractMonth", workTimeService.setCalenderObject(calender, workTimeList));
		model.addAttribute("contractId", contractId);
		model.addAttribute("yearMonth", dateTimeUtil.toStringDate(minWorkDay, "yyyy年MM月"));
		model.addAttribute("yearMonthUrl", yearMonth);
		model.addAttribute("workTimes", workTimeMonth);
		model.addAttribute("displayStatus", contractService.selectDisplay(yearMonth, contractId, LocalDate.now()));
		model.addAttribute("WorkTimeForm", contractService.setWorkTimeForm(form, userId));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/contractDay";
	}

	@PostMapping("/contract/{contractId}/{yearMonth}")
	public String postContractDay(@ModelAttribute WorkTime form, Model model,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);

		workTimeService.deleteOne(form.getWorkTimeId());

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "redirect:/contract/{contractId}/{yearMonth}";
	}

	@GetMapping("/contract/{contractId}/{yearMonth}/changeRequestStatus")
	public String changeRequestStatus(@PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth) {

		Month month = monthService.selectMonthTable(contractId, yearMonth);
		monthService.update(monthService.changeRequest(month));

		return "redirect:/contract/{contractId}/{yearMonth}";
	}
}