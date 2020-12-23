package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.model.WorkTimeForm;
import com.example.demo.login.domain.repository.WorkTimeDao;
import com.example.demo.login.domain.service.util.DateTimeUtil;

@Service
public class WorkTimeService {

	@Autowired
	WorkTimeDao dao;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@Autowired
	ContractService contractService;

	@Autowired
	MonthService monthService;

	public void insertOne(WorkTime workTime) {

		dao.insertOne(workTime);
	}

	public void updateOne(WorkTime workTime) {

		dao.updateOne(workTime);
	}

	public List<WorkTime> selectMonthData(int userId) {

		return dao.selectMonthData(userId);
	}

	public int getWorkTimeMinute(WorkTimeForm form) {

		int startTimeMinute = form.getStartTime().get(ChronoField.MINUTE_OF_DAY);
		int breakTimeMinute = form.getBreakTime().get(ChronoField.MINUTE_OF_DAY);
		int endTimeMinute = form.getEndTime().get(ChronoField.MINUTE_OF_DAY);
		int workTimeMinute = endTimeMinute - (startTimeMinute + breakTimeMinute);

		return workTimeMinute;
	}

	public WorkTime setWorkTime(WorkTimeForm form, int userId) {

		int contractId = contractService.latestContract(userId).getContractId();
		int monthId = monthService.latestMonth(userId).getMonthId();

		WorkTime workTime = new WorkTime();
		workTime.setContractId(contractId);
		workTime.setMonthId(monthId);
		workTime.setWorkTimeMinute(getWorkTimeMinute(form));
		workTime.setWorkDay(form.getWorkDay());
		workTime.setStartTime(LocalDateTime.of(form.getWorkDay(), form.getStartTime()));
		workTime.setBreakTime(form.getBreakTime());

		int overTimeMinute = 1440;
		if (form.isOverTimeFlag()) {
			workTime.setEndTime(LocalDateTime.of(form.getWorkDay().plusDays(1), form.getEndTime()));
			workTime.setWorkTimeMinute(getWorkTimeMinute(form) + overTimeMinute);

		} else {
			workTime.setEndTime(LocalDateTime.of(form.getWorkDay(), form.getEndTime()));
			workTime.setWorkTimeMinute(getWorkTimeMinute(form));
		}

		return workTime;
	}

	public List<String> selectCalendar() {

		List<String> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = 1;
		int loopCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < loopCount; i++) {
			calendar.set(year, month - 1, day + i);
			list.add(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.DATE));
		}

		return list;
	}

	public List<WorkTime> selectMany(int contractId) {

		return dao.selectMany(contractId);
	}

	public List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay) {

		return dao.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
	}

	public int samWorkTimeMinute(List<WorkTime> workTimes) {

		int workTimesSize = workTimes.size();
		int samMinute = 0;
		for (int i = 0; i < workTimesSize; i++) {
			samMinute += workTimes.get(i).getWorkTimeMinute();
		}

		return samMinute;
	}

	public LinkedHashMap<String, Object> calender(String yearMonth) {

		LocalDate lastDayOfMonth = dateTimeUtil.BeginningOfMonth(yearMonth).with(TemporalAdjusters.lastDayOfMonth());
		int maxDay = lastDayOfMonth.getDayOfMonth();

		LocalDate BeginningOfMonth = dateTimeUtil.BeginningOfMonth(yearMonth);
		DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LinkedHashMap<String, Object> calender = new LinkedHashMap<>();
		for (int i = 0; i < maxDay; i++) {
			LocalDate date = BeginningOfMonth.plusDays(i);
			String stringDate = datetimeformatter.format(date);
			WorkTime workTime = new WorkTime();
			workTime.setWorkDay(date);
			calender.put(stringDate, workTime);
		}

		return calender;
	}

	public LinkedHashMap<String, Object> setCalenderObject(LinkedHashMap<String, Object> calender,
			List<Map<String, Object>> workTimeMonth) {

		for (Map<String, Object> workTime : workTimeMonth) {
			calender.put(workTime.get("workDay").toString(), workTime);
		}

		return calender;
	}

	public List<Map<String, Object>> changeTimeFormat(List<WorkTime> workTimeMonth) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPANESE);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (WorkTime map : workTimeMonth) {
			Map<String, Object> workTimeObject = new HashMap<>();
			workTimeObject.put("workDay", map.getWorkDay());
			workTimeObject.put("startTime", map.getStartTime().format(timeFormat));
			workTimeObject.put("endTime", map.getEndTime().format(timeFormat));
			workTimeObject.put("breakTime", map.getBreakTime());
			workTimeObject.put("workTimeMinute", map.getWorkTimeMinute());

			list.add(workTimeObject);
		}

		return list;
	}

	public void deleteOne(int workTimeId) {

		dao.deleteOne(workTimeId);
	}

	public boolean hasExist(WorkTime workTime) {

		return dao.hasExist(workTime);
	}

	public String changeFormat(LocalDate timeDate) {

		DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPANESE);
		String stringTime = timeDate.format(timeFormat);

		return stringTime;
	}
}