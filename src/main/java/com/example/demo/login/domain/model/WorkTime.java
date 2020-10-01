package com.example.demo.login.domain.model;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.HashMap;

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
	
	public static int workTimeMinute(WorkTimeForm form) {
		
		HashMap<String, Integer> workTimeMinute = new HashMap<String, Integer>();
		
		// 始業時間（分）
		workTimeMinute.put("startTimeMinute", form.getStartTime().get(ChronoField.MINUTE_OF_DAY));
		
		// 休憩時間（分）
		workTimeMinute.put("breakTimeMinute", form.getBreakTime().get(ChronoField.MINUTE_OF_DAY));
		
		// 終業時間（分）
		workTimeMinute.put("endTimeMinute", form.getEndTime().get(ChronoField.MINUTE_OF_DAY));
		
		// 勤務時間の計算（分）
		workTimeMinute.put("workTimeMinute", workTimeMinute.get("endTimeMinute") - (workTimeMinute.get("startTimeMinute") + workTimeMinute.get("breakTimeMinute")));
		
		return workTimeMinute.get("workTimeMinute");
	}
	
	
	public void setWorkTime(WorkTimeForm form) {
		
		this.setWorkDay(form.getWorkDay());
		this.setStartTime(LocalDateTime.of(form.getWorkDay(), form.getStartTime()));
		this.setBreakTime(form.getBreakTime());	
		
		if (form.isOverTimeFlag()) {
			this.setEndTime(LocalDateTime.of(this.getWorkDay().plusDays(1), form.getEndTime()));
			this.setWorkTimeMinute(workTimeMinute(form) + 1440);
		} else {
			this.setEndTime(LocalDateTime.of(form.getWorkDay(), form.getEndTime()));
			this.setWorkTimeMinute(workTimeMinute(form));
		}
	}
}