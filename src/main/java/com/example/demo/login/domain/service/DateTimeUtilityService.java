package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DateTimeUtilityService {

	@Autowired
	WorkTimeService workTimeService;

	public Map<String, Integer> getYearAndMonth(String yearMonth) {
		
		LocalDate createDate = workTimeService.BeginningOfMonth(yearMonth);
		int year = createDate.getYear();
		int month = createDate.getMonthValue();

		Map<String, Integer> map = new HashMap<>();
		map.put("year", year);
		map.put("month", month);

		return map;
	}

}