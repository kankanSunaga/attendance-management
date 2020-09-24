package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.service.ContractService;

@Controller
public class ContractListController {
	
	@Autowired
	ContractService contractService;
	
	@GetMapping("/contractList")//←{userId}で動的にする
	public String getContractList(Model model){
		
		model.addAttribute("contents","login/contractList::login_contents");
		return "login/headerLayout";
	}
	
	@GetMapping("/contractMonth")//←{contractId}で動的にする
	public String getContractMonth(Model model){
		
		return "login/contractMonth";
	}
	
	@GetMapping("/contractDay")//←{workTimeId}で動的にする
	public String getContractDay(Model model){
		
		return "login/contractDay";
	}
}

