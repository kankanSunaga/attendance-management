package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

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

	// insert用メソッド
	public void insertOne(WorkTime workTime) {

		dao.insertOne(workTime);
	}

	// その月のデータ取得用メソッド
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

		WorkTime workTime = new WorkTime();
		int overTime = 1440;

		workTime.setWorkTimeMinute(getWorkTimeMinute(form));

		workTime.setWorkDay(form.getWorkDay());
		workTime.setStartTime(LocalDateTime.of(form.getWorkDay(), form.getStartTime()));
		workTime.setBreakTime(form.getBreakTime());

		if (form.isOverTimeFlag()) {
			workTime.setEndTime(LocalDateTime.of(form.getWorkDay().plusDays(1), form.getEndTime()));
			workTime.setWorkTimeMinute(getWorkTimeMinute(form) + overTime);
		} else {
			workTime.setEndTime(LocalDateTime.of(form.getWorkDay(), form.getEndTime()));
			workTime.setWorkTimeMinute(getWorkTimeMinute(form));
		}
		return workTime;
	}

	public List<String> selectCalendar() {
		// 年月日を設定
		List<String> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		// +１しないと欲しい値が取れないのでしてます。(仕様です)
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = 1;
		// 作成する日数
		int loopCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 0; i < loopCount; i++) {
			calendar.set(year, month - 1, day + i);
			list.add(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1) + "/"
					+ calendar.get(Calendar.DATE));
		}
		return list;
	}

	// 月のデータ全件取得用メソッド
	public List<WorkTime> selectMany(int contractId) {
		return dao.selectMany(contractId);
	}

	// 月のデータ全件取得用メソッド（範囲検索）
	public List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay) {
		return dao.rangedSelectMany(contractId, minWorkDay, maxWorkDay);
	}

	// 月の勤務時間（分）の合計を返す
	public int samWorkTimeMinute(List<WorkTime> workTimes) {

		int workTimesSize = workTimes.size();
		int samMinute = 0;

		for (int i = 0; i < workTimesSize; i++) {
			samMinute += workTimes.get(i).getWorkTimeMinute();
		}
		return samMinute;
	}

	// 空の月のカレンダー作成(1~月末)
	public LinkedHashMap<String, Object> calender(String yearMonth) {

		LinkedHashMap<String, Object> calender = new LinkedHashMap<>();

		// 月の最大日数
		LocalDate lastDayOfMonth = dateTimeUtil.BeginningOfMonth(yearMonth).with(TemporalAdjusters.lastDayOfMonth());
		int maxDay = lastDayOfMonth.getDayOfMonth();

		LocalDate BeginningOfMonth = dateTimeUtil.BeginningOfMonth(yearMonth);

		DateTimeFormatter datetimeformatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		for (int i = 0; i < maxDay; i++) {

			LocalDate date = BeginningOfMonth.plusDays(i);

			String stringDate = datetimeformatter.format(date);

			WorkTime workTime = new WorkTime();

			workTime.setWorkDay(date);

			calender.put(stringDate, workTime);

		}
		return calender;
	}

	// 空のカレンダーにデータをセット
	public LinkedHashMap<String, Object> setCalenderObject(LinkedHashMap<String, Object> calender, int contractId,
			String yearMonth) {

		LocalDate minDay = dateTimeUtil.BeginningOfMonth(yearMonth);
		LocalDate maxDay = minDay.with(TemporalAdjusters.lastDayOfMonth());

		List<WorkTime> workTimes = rangedSelectMany(contractId, minDay, maxDay);

		for (WorkTime workTime : workTimes) {
			calender.put(workTime.getWorkDay().toString(), workTime);
		}
		return calender;
	}
}
