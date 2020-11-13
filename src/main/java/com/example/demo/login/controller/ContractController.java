package com.example.demo.login.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class ContractController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	SessionUtil sessionUtil;

	@GetMapping("/contract")
	public String getContract(@ModelAttribute ContractForm form, Model model, HttpServletRequest request,
			HttpServletResponse response) {

		return "login/contract";
	}

	@PostMapping("/contract")
	public String postContract(@ModelAttribute @Validated ContractForm form, BindingResult bindingResult,
			HttpServletRequest request, Model model) {

		if (bindingResult.hasErrors()) {
			return getContract(form, model, null, null);
		}

		int userId = sessionUtil.getUserId(request);

		form.setUserId(userId);

		Contract contract = contractService.setInsertOne(form);

		contractService.insertOne(contract);

		// 勤務開始日に以前か以降かのチェック
		LocalDate formDate = form.getStartDate();
		LocalDate currentDate = LocalDate.now();

		if (formDate.compareTo(currentDate) > 0) {
			return "login/startDateWaiting";
		} else {
			return "login/login";
		}
	}
}