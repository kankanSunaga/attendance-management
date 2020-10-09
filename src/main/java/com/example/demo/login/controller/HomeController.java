package com.example.demo.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	private WorkTimeService workTimeService;
	
	@GetMapping("/home")
	public String getHome(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
				
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		
		//現在の日付を取得
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatChange = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String formatChangedToday = today.format(formatChange);
		model.addAttribute("today", formatChangedToday);
		
		//月の合計勤務時間を算出
		List<WorkTime> monthDataList= workTimeService.selectMonthData();
		int totalWorkTimeMinute = 0;
		for (int i = 0; i < monthDataList.size(); i++) {
			  WorkTime workTime = monthDataList.get(i);
			  int workTimeMinute = workTime.getWorkTimeMinute();
			  totalWorkTimeMinute += workTimeMinute;
		}
		//その月の全ての日数を取得
		List<String>  calendar = workTimeService.selectCalendar();
		
		model.addAttribute("calendar",calendar);
				
		int totalWorkTimeInt = totalWorkTimeMinute / 60; //時間
		int totalWorkTimeDouble = totalWorkTimeMinute % 60; //分
		model.addAttribute("totalWorkTimeInt", totalWorkTimeInt);
		model.addAttribute("totalWorkTimeDouble", totalWorkTimeDouble);
		
		//月のデータを取得する際に使用可能
		model.addAttribute("monthDataList",monthDataList);
		
		//契約勤務時間を取得（Contractテーブルより取得）
		Contract contract = contractService.selectOne();
		int contractTime = contract.getContractTime();
		
		//残りの契約時間の算出（契約時間から月の合計勤務時間を引く）
		int remainingMinute = contractTime * 60 - totalWorkTimeMinute; //残り時間（分）
		int remainingTimeInt = remainingMinute / 60; //時間
		int remainingTimeDouble = remainingMinute % 60; //分
		model.addAttribute("remainingTimeInt", remainingTimeInt);
		model.addAttribute("remainingTimeDouble", remainingTimeDouble);
		
		return "login/home";
	}
	
	@PostMapping("/home")
	public String postHome(Model model) {
		
		return "login/home";
	}
}