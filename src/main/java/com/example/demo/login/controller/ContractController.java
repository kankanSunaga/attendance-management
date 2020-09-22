package com.example.demo.login.controller;

import java.time.LocalDate;
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

@Controller
public class ContractController {
	
	@Autowired
	private ContractService contractService;
	
	@GetMapping("/contract")
	public String getContract(@ModelAttribute ContractForm form, Model model) {
		return "login/contract";
	}
    
	@PostMapping("/contract")
	public String postContract(@ModelAttribute @Validated ContractForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {
			return getContract(form, model);
		}
		
		System.out.println(form);
		
		//insert用変数
		Contract contract = new Contract();
		
		contract.setContractTime(form.getContractTime());
		contract.setStartTime(form.getStartTime());
		contract.setBreakTime(form.getBreakTime());
		contract.setEndTime(form.getEndTime());
		contract.setStartDate(form.getStartDate());
		contract.setOfficeName(form.getOfficeName());

		boolean result = contractService.insert(contract);

		if(result == true) {
			System.out.println("insert成功");
		} else {
			System.out.println("insert失敗");
		}
		
		//勤務開始日に以前か以降かのチェック
		LocalDate formDate = form.getStartDate();
		LocalDate currentDate = LocalDate.now();
		
		if(formDate.compareTo(currentDate) > 0) {
			return "login/startDateWaiting";
		} else {
			return "login/login";
		}
	}
}