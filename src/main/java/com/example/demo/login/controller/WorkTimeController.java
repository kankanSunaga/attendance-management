package com.example.demo.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.WorkTimeForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.example.demo.login.domain.service.transaction.WorkTimeTransaction;
import com.example.demo.login.domain.service.util.DateTimeUtil;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class WorkTimeController {

	@Autowired
	UserService userService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	ContractService contractService;

	@Autowired
	MonthService monthService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@Autowired
	WorkTimeTransaction workTimeTransaction;

	@Autowired
	UserIconService userIconService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/workTime")
	public String getWorkTime(@ModelAttribute WorkTimeForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		model.addAttribute("WorkTimeForm", contractService.setWorkTimeForm(form, userId));
		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/workTime";
	}

	@PostMapping("/workTime")
	public String postWorkTime(@ModelAttribute WorkTimeForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (!(workTimeService.hasExist(workTimeService.setWorkTime(form, userId)))) {
			workTimeService.updateOne(workTimeService.setWorkTime(form, userId));

		} else if (dateTimeUtil.checkYearMonth(userId)) {
			workTimeService.insertOne(workTimeService.setWorkTime(form, userId));

		} else if (!(dateTimeUtil.checkYearMonth(userId))) {
			workTimeTransaction.insertMonthAndWork(form, userId);
		}

		model.addAttribute("base64", userIconService.uploadImage(userId));
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "login/workTime";
	}
}