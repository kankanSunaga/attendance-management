package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.model.WorkTimeForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.WorkTimeService;

@Controller
public class WorkTimeController {
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@Autowired
	private ContractService contractService;
	
	@GetMapping("/workTime")
	public String getWorkTime(@ModelAttribute WorkTimeForm form, Model model) {
			
		Contract contract = contractService.selectOne();
		
		
		form.setStartTime(contract.getStartTime());
		form.setBreakTime(contract.getBreakTime());
		form.setEndTime(contract.getEndTime());
		
		
		System.out.println(form);
		
		model.addAttribute("WorkTimeForm",form);
		
		
		return "login/workTime";
	}
	

	@PostMapping("/workTime")
	public String postWorkTime(@ModelAttribute WorkTimeForm form, Model model) {
		
		WorkTime workTime = new WorkTime();
		
		workTime.setWorkTime(form);
		
		workTimeService.insert(workTime);
		
		return "login/workTime";
	}
}