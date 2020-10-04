package com.example.demo.login.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.WorkTimeService;

@Controller
public class ContractListController {
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	WorkTimeService workTimeService;
	
	@GetMapping("/contracts")//sessionでuserId渡されるため静的URL
	public String getContractList(Model model) {
		
		List<Contract> contractList = contractService.selectMany();
		
		model.addAttribute("contractList", contractList);
		model.addAttribute("contents","login/contractList::login_contents");
		
		return "login/headerLayout";
	}
	
	@GetMapping("/contract/{contractId}")
	public String getContractMonth(@ModelAttribute WorkTime form, Model model, @PathVariable("contractId")int contractId){
		
		List<WorkTime> contractMonthList = workTimeService.selectMany(contractId);
		
		//年月を表示させる
		List<String> monthList = new ArrayList<>();
		for(int i = 0; i < contractMonthList.size(); i++) {
			//workDayの取り出し
			WorkTime workTime = contractMonthList.get(i);
			LocalDate localDate = workTime.getWorkDay();
			
			//フォーマット変換
			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月");
			String month = localDate.format(format);		
			monthList.add(month);
		}
		
		//重複している情報を順番を変えずに削除
		List<String> orderMonthList = new ArrayList<String>(new LinkedHashSet<>(monthList));
		
		model.addAttribute("contractMonth", orderMonthList);
		
		//会社名を取得
		Contract contract = contractService.activeSelectOne(contractId);
		String officeName = contract.getOfficeName() ;
		model.addAttribute("officeName", officeName);
		
		model.addAttribute("contents", "login/contractMonth::login_contents");
		
		return "login/headerLayout";
	}
	
	@GetMapping("/contracts/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute Contract form, Model model, @PathVariable("contractId")int contractId, @PathVariable("yearMonth")int yearMonth){
		
		return "login/contracts/{contractId}/{yearMonth}";
	}
}

