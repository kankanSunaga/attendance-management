package com.example.demo.login.domain.model;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class WorkTime {

	private int workTimeId;
	private LocalDate workDay;
	private LocalDateTime startTime;
	private LocalTime breakTime;
	private LocalDateTime endTime;
	private int workTimeMinute;
	private int contractId;
	private int monthId;

	// 変換したデータ(List内のデータ)
	private int day;
	private String dayOfWeek;
	private String stringStartTime;
	private String stringEndTime;
}