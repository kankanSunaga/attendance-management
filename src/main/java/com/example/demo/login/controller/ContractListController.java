package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.service.ContractService;

@Controller
public class ContractListController {
	
	@Autowired
	ContractService contractService;
	
	@GetMapping("/contracts")//sessionでuserId渡されるため静的URL
	public String getContractList(@ModelAttribute Contract form, Model model) {
		
		Contract contract = contractService.selectOne();
		
		//Contractクラスをフォームクラスに変換
		form.setContractId(contract.getContractId());
		form.setStartDate(contract.getStartDate());
		form.setOfficeName(contract.getOfficeName());
		form.setEndDate(contract.getEndDate());
		
		//Modelに登録
		model.addAttribute("contractList",form);
		
		model.addAttribute("contents","login/contractList::login_contents");
		return "login/headerLayout";
	}
	
	@GetMapping("/contract/{contractId}")
	public String getContractMonth(@ModelAttribute Contract form, Model model, @PathVariable("contractId")int contractId){
		
		Contract contract = contractService.selectOne();
		
		//Contractクラスをフォームクラスに変換
		form.setContractId(contract.getContractId());
		
		//Modelに登録
		model.addAttribute("signupForm",form);
		
		return "login/contracts/{contractId}";
	}
	
	@GetMapping("/contracts/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute Contract form, Model model, @PathVariable("contractId")int contractId, @PathVariable("yearMonth")int yearMonth){
		
		return "login/contractDay";
	}
}

