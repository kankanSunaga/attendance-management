package com.example.demo.login.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.WorkTimeService;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class ContractListController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	MonthService monthService;

	@Autowired
	SessionUtil sessionUtil;

	@Autowired
	UserIconService userIconService;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/contracts")
	public String getContractList(Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		List<Contract> contractList = contractService.selectMany(userId);

		model.addAttribute("contractList", contractList);
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/contractList";
	}

	@GetMapping("/contract/{contractId}")
	public String getContractMonth(@ModelAttribute WorkTime form, Model model,
			@PathVariable("contractId") int contractId) throws IOException {

		int userId = sessionUtil.getUserId(request);

		List<WorkTime> contractMonthList = workTimeService.selectMany(contractId);

		// 「yyyy年MM月」と「yyyyMM」のStringを作成する処理
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		for (int i = 0; i < contractMonthList.size(); i++) {
			Map<String, String> map = new HashMap<String, String>();
			WorkTime workTime = contractMonthList.get(i);
			LocalDate localDate = workTime.getWorkDay();

			// 2つのフォーマットの作成とLocalDateからStringに型の変換
			DateTimeFormatter viewFormat = DateTimeFormatter.ofPattern("yyyy年MM月");
			DateTimeFormatter urlFormat = DateTimeFormatter.ofPattern("yyyyMM");
			String listYearMonth = localDate.format(viewFormat);
			String urlYearMonth = localDate.format(urlFormat);

			map.put("view", listYearMonth);
			map.put("url", urlYearMonth);
			list.add(map);
		}

		model.addAttribute("contractMonth", list);

		// 会社名を取得
		Contract contract = contractService.activeSelectOne(contractId);
		String officeName = contract.getOfficeName();
		model.addAttribute("officeName", officeName);
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/contractMonth";
	}

	@GetMapping("/contract/{contractId}/{yearMonth}")
	public String getContractDay(@ModelAttribute WorkTime form, Model model, @PathVariable("contractId") int contractId,
			@PathVariable("yearMonth") String yearMonth) throws IOException {

		int userId = sessionUtil.getUserId(request);

		// yyyy-MM-01(月初のString)の作成
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(yearMonth);
		stringBuilder.insert(4, "-");
		stringBuilder.append("-01");
		String strMinWorkDay = stringBuilder.toString();

		// 引数のminWorkDayとmaxWorkDayの値を代入
		LocalDate minWorkDay = LocalDate.parse(strMinWorkDay, DateTimeFormatter.ISO_DATE);
		LocalDate maxWorkDay = minWorkDay.with(TemporalAdjusters.lastDayOfMonth());

		List<WorkTime> contractDayList = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
		model.addAttribute("contractDay", contractDayList);

		// yyyy年MM月の作成
		String strMonth = strMinWorkDay.replace("-01", "月");
		String strYearMonth = strMonth.replace("-", "年");

		model.addAttribute("contractId", contractId);
		model.addAttribute("yearMonth", strYearMonth);
		model.addAttribute("yearMonthUrl", yearMonth);

		List<WorkTime> workTimes = workTimeService.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
		model.addAttribute("workTimes", workTimes);

		// 空のカレンダー作成
		LinkedHashMap<String, Object> calender = workTimeService.calender(yearMonth);
		// 空のカレンダーにデータを追加
		LinkedHashMap<String, Object> setCalenderObject = workTimeService.setCalenderObject(calender, contractId,
				yearMonth);

		String displayStatus = contractService.selectDisplay(yearMonth, userId, contractId, LocalDate.now());

		model.addAttribute("contract", setCalenderObject);
		model.addAttribute("totalTime", workTimeService.samWorkTimeMinute(workTimes));
		model.addAttribute("displayStatus", displayStatus);
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "login/contractDay";
	}

	@PostMapping("/contract/{contractId}/{yearMonth}")
	public String postContractDay(@ModelAttribute WorkTime form, Model model,
			@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth)
			throws IOException {

		int userId = sessionUtil.getUserId(request);

		workTimeService.deleteOne(form.getWorkTimeId());
		model.addAttribute("base64", userIconService.uploadImage(userId));

		return "redirect:/contract/{contractId}/{yearMonth}";
	}
	
	@GetMapping("/contract/{contractId}/{yearMonth}/changeRequestStatus")
	public String changeRequestStatus(@PathVariable("contractId") int contractId, @PathVariable("yearMonth") String yearMonth) {
		
		int userId = sessionUtil.getUserId(request);
		
		Month month = monthService.selectMonthTable(userId, contractId, yearMonth);
		monthService.update(monthService.changeRequest(month));
		
		return "redirect:/contract/{contractId}/{yearMonth}";
	}
}
