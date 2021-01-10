package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.MonthDao;
import com.example.demo.login.domain.service.util.DateTimeUtil;

@Service
public class MonthService {

	@Autowired
	MonthDao dao;

	@Autowired
	DayOfWeekService dayOfWeekService;

	@Autowired
	MonthService monthService;

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	ContractService contractService;

	@Autowired
	DateTimeUtil dateTimeUtil;

	public List<User> getRequestUsers() {

		return dao.getRequestUsers();
	}

	public int ruquestUserCount() {

		return getRequestUsers().size();
	}

	public void updateToDeadline() {

		LocalDate lastMonthDate = LocalDate.now().minusMonths(1);
		dao.updateToDeadline(lastMonthDate.getYear(), lastMonthDate.getMonthValue());
	}

	public boolean deadlineCheck(boolean deadlineStatus, LocalDate lastWeekDay, LocalDate nowDate) {

		if (deadlineStatus) {
			return true;
		} else if (nowDate.equals(lastWeekDay) || nowDate.isAfter(lastWeekDay)) {
			return false;
		} else {
			return true;
		}
	}

	public Month latestMonth(int userId) {

		return dao.latestMonth(userId);
	}

	public Month selectMonthTable(int contractId, String yearMonth) {

		return dao.selectMonthTable(contractId, yearMonth);
	}

	public void insertOne(Month month) {

		dao.insertOne(month);
	}

	public Month setMonth(int userId) {

		LocalDate nowDate = LocalDate.now();
		String nowYearMonth = dateTimeUtil.toStringDate(nowDate, "yyyyMM");
		int year = dateTimeUtil.getYearAndMonth(nowYearMonth).get("year");
		int month = dateTimeUtil.getYearAndMonth(nowYearMonth).get("month");

		int contractId = contractService.latestContract(userId).getContractId();

		Month monthData = new Month();
		monthData.setYear(year);
		monthData.setMonth(month);
		monthData.setDeadlineStatus(false);
		monthData.setRequestStatus(false);
		monthData.setContractId(contractId);

		return monthData;
	}

	public void update(Month month) {

		dao.update(month);
	}

	public Month changeRequest(Month month) {

		month.setRequestStatus(true);

		return month;
	}

	public List<Month> getMonthList(int contractId) {

		return dao.getMonthList(contractId);
	}

	public List<Map<String, String>> getMonthDate(List<Month> monthList) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int size = monthList.size();
		for (int i = 0; i < size; i++) {
			Map<String, String> map = new HashMap<String, String>();
			Month month = monthList.get(i);
			int getYear = month.getYear();
			int getMonth = month.getMonth();

			String stringYearMonth = dateTimeUtil.toStringYearMonth(getYear, getMonth);
			LocalDate localDate = dateTimeUtil.BeginningOfMonth(stringYearMonth);
			String stringDate = dateTimeUtil.toStringDate(localDate, "yyyy年MM月");

			map.put("view", stringDate);
			map.put("url", stringYearMonth);
			list.add(map);
		}

		return list;
	}

	public List<WorkTime> getWorkTime(int contractId, int monthId) {

		return dao.getWorkMonth(contractId, monthId);
	}

	public int getTotalTime(List<WorkTime> workTime) {

		int totalTime = 0;
		for (int i = 0; i < workTime.size(); i++) {
			totalTime += workTime.get(i).getWorkTimeMinute();
		}

		return totalTime;
	}

	public String checkQuota(int contractTime, int totalTime) {

		int result = contractTime - totalTime;
		if (result > 0) {
			int hour = result / 60;
			int minute = result % 60;

			return hour + "時間" + minute + "分";
		}

		return "達成しました";
	}
	
	public List<WorkTime> getMonth(int monthId) {
		
		return dao.getMonth(monthId);
	}
	
	public Month deadlineStatus(Month month) {

		month.setDeadlineStatus(true);

		return month;	
	}
}