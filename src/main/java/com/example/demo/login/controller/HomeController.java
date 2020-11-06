package com.example.demo.login.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.DayOfWeekService;
import com.example.demo.login.domain.service.MonthService;
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

	@Autowired
	private DayOfWeekService dayOfWeekService;

	@Autowired
	private MonthService monthService;

	@GetMapping("/home")
	public String getHome(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {

		// セッションの保持(userId)
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		// 現在の日付を取得
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatChange = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
		String formatChangedToday = today.format(formatChange);
		model.addAttribute("today", formatChangedToday);

		// 月の合計勤務時間を算出
		List<WorkTime> monthDataList = workTimeService.selectMonthData(userId);
		int totalWorkTimeMinute = 0;
		for (int i = 0; i < monthDataList.size(); i++) {
			WorkTime workTime = monthDataList.get(i);
			int workTimeMinute = workTime.getWorkTimeMinute();
			totalWorkTimeMinute += workTimeMinute;
		}
		// その月の全ての日数を取得
		List<String> calendar = workTimeService.selectCalendar();

		model.addAttribute("calendar", calendar);

		int totalWorkTimeInt = totalWorkTimeMinute / 60; // 時間
		int totalWorkTimeDouble = totalWorkTimeMinute % 60; // 分
		model.addAttribute("totalWorkTimeInt", totalWorkTimeInt);
		model.addAttribute("totalWorkTimeDouble", totalWorkTimeDouble);

		// 月のデータを取得する際に使用可能
		model.addAttribute("monthDataList", monthDataList);

		// 契約勤務時間を取得（Contractテーブルより取得）
		Contract contract = contractService.latestContract(userId);
		int contractTime = contract.getContractTime();

		// 残りの契約時間の算出（契約時間から月の合計勤務時間を引く）
		int remainingMinute = contractTime * 60 - totalWorkTimeMinute; // 残り時間（分）
		int remainingTimeInt = remainingMinute / 60; // 時間
		int remainingTimeDouble = remainingMinute % 60; // 分
		model.addAttribute("remainingTimeInt", remainingTimeInt);
		model.addAttribute("remainingTimeDouble", remainingTimeDouble);

		// 現在のDateを取得
		LocalDate nowDate = LocalDate.now();

		// 各ID取得
		int contractId = contractService.latestContract(userId).getContractId();
		int monthId = monthService.latestMonth(userId).getMonthId();

		// 最終平日判定
		LocalDate lastWeekDay = dayOfWeekService.getLastWeekDay(nowDate);

		// ステータス情報取得
		boolean deadline = monthService.deadlineCheck(userId, contractId, monthId);

		// modelにつっこむ
		model.addAttribute("lastWeekDay", lastWeekDay);
		model.addAttribute("deadline", deadline);

		return "login/home";
	}
}