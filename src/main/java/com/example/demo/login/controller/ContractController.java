package com.example.demo.login.controller;

import java.time.LocalDate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

	@Autowired
	HttpServletRequest request;

	@GetMapping("/contract")
	public String getContract(@ModelAttribute ContractForm form) {

		return "login/contract";
	}

	@PostMapping("/contract")
	public String postContract(@ModelAttribute @Validated ContractForm form, BindingResult bindingResult) {

		int userId = sessionUtil.getUserId(request);

		if (bindingResult.hasErrors()) {
			return getContract(form);
		}

		Contract contract = contractService.setInsertOne(form, userId);
		contractService.insertOne(contract);

		LocalDate formDate = form.getStartDate();
		if (formDate.compareTo(LocalDate.now()) > 0) {
			return "login/startDateWaiting";
		} else {
			return "login/login";
		}
	}
}