package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Service
public class WorkTimeService {

	@Autowired
	WorkTimeDao dao;

	// insert用メソッド
	public boolean insert(WorkTime workTime) {

		int rowNumber = dao.insertOne(workTime);

		return rowNumber > 0;
	}

	// その月のデータ取得用メソッド
	public List<WorkTime> selectMonthData(int userId) {
		return dao.selectMonthData(userId);
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

	
	// 月の１日のデータを取得(yyyyMMdd)
	public LocalDate BeginningOfMonth(String yearMonth) {
		
		String strYearMonthDay = yearMonth + "01";
		LocalDate BeginningOfMonth = LocalDate.parse(strYearMonthDay, DateTimeFormatter.ofPattern("yyyyMMdd"));
		
		return BeginningOfMonth;
	}
	
	// 空の月のカレンダー作成(1~月末)
	public LinkedHashMap<String, Object> calender(String yearMonth) {

		LinkedHashMap<String, Object> calender = new LinkedHashMap<>();

		

		// 月の最大日数
		LocalDate lastDayOfMonth = BeginningOfMonth(yearMonth).with(TemporalAdjusters.lastDayOfMonth());
		int maxDay = lastDayOfMonth.getDayOfMonth();
		
		LocalDate BeginningOfMonth = BeginningOfMonth(yearMonth);
		
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
	public LinkedHashMap<String, Object> setCalenderObject(LinkedHashMap<String, Object> calender, int contractId, String yearMonth) {
		
		LocalDate minDay = BeginningOfMonth(yearMonth);
		LocalDate maxDay = minDay.with(TemporalAdjusters.lastDayOfMonth());
		
		List<WorkTime> workTimes = rangedSelectMany(contractId, minDay, maxDay);
		
		
		for (WorkTime workTime : workTimes) {			
			calender.put(workTime.getWorkDay().toString(), workTime);
		}
		return calender;
	}
}
