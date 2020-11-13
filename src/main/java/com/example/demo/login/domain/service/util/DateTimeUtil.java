package com.example.demo.login.domain.service.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.service.WorkTimeService;

@Service
public class DateTimeUtil {

	@Autowired
	WorkTimeService workTimeService;

	public Map<String, Integer> getYearAndMonth(String yearMonth) {
		
		LocalDate createDate = BeginningOfMonth(yearMonth);
		int year = createDate.getYear();
		int month = createDate.getMonthValue();

		Map<String, Integer> map = new HashMap<>();
		map.put("year", year);
		map.put("month", month);

		return map;
	}
	
	public String toStringDate(LocalDate localDate, String format) {

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
        
        return localDate.format(dateTimeFormatter);
    }
	
	public String toStringYearMonth(int year, int month) {

		String stringYear = Integer.toString(year);
		String stringMonth = String.format("%02d", month);		
		String stringYearMonth = stringYear + stringMonth;
		
		return stringYearMonth;
	}
	
	public LocalDate BeginningOfMonth(String yearMonth) {

		String strYearMonthDay = yearMonth + "01";
		LocalDate BeginningOfMonth = LocalDate.parse(strYearMonthDay, DateTimeFormatter.ofPattern("yyyyMMdd"));

		return BeginningOfMonth;
	}
}