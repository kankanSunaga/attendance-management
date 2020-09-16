package com.example.demo.login.domain.model;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.HashMap;

import lombok.Data;

@Data
public class WorkTime {	
	
	private LocalDate workDay;
	private LocalDateTime startTime;
	private LocalTime breakTime;
	private LocalDateTime endTime;
	private int totalTimeMinute;
	
	
	public static int totalTimeMinute(WorkTimeForm form) {
		
		HashMap<String, Integer> totalTimeMinute = new HashMap<String, Integer>();
		
		// 始業時間（分）
		totalTimeMinute.put("startHour", form.getStartTime().getHour() * 60);
		totalTimeMinute.put("startMinute", form.getStartTime().getMinute());
		totalTimeMinute.put("startTotalMinute", totalTimeMinute.get("startHour") + totalTimeMinute.get("startMinute"));
		
		// 休憩時間（分）
		totalTimeMinute.put("breakHour", form.getBreakTime().getHour() * 60);
		totalTimeMinute.put("breakMinute", form.getBreakTime().getMinute());
		totalTimeMinute.put("breakTotalMinute", totalTimeMinute.get("breakHour") + totalTimeMinute.get("breakMinute"));
		
		// 終業時間（trueの場合+24時間）
		if (form.isOverTimeFlag()) {
			totalTimeMinute.put("endHourFlag", form.getEndTime().getHour() + 24);
		} else {
			totalTimeMinute.put("endHourFlag", form.getEndTime().getHour());
		}
		
		// 終業時間（分）
		totalTimeMinute.put("endHour", totalTimeMinute.get("endHourFlag") * 60);
		totalTimeMinute.put("endMinute", form.getEndTime().getMinute());
		totalTimeMinute.put("endTotalMinute", totalTimeMinute.get("endHour") + totalTimeMinute.get("endMinute"));
		
		// 勤務時間の計算（分）
		totalTimeMinute.put("totalTimeMinute", totalTimeMinute.get("endTotalMinute") - (totalTimeMinute.get("startTotalMinute") + totalTimeMinute.get("breakTotalMinute")));
		
		return totalTimeMinute.get("totalTimeMinute");
	}
	
	
	public void setWorkTime(WorkTimeForm form) {
		
		this.setWorkDay(form.getWorkDay());
		this.setStartTime(LocalDateTime.of(form.getWorkDay(), form.getStartTime()));
		this.setBreakTime(form.getBreakTime());	
		
		if (form.isOverTimeFlag()) {
			this.setEndTime(LocalDateTime.of(this.getWorkDay().plusDays(1), form.getEndTime()));
			this.setTotalTimeMinute(totalTimeMinute(form));
		} else {
			this.setEndTime(LocalDateTime.of(form.getWorkDay(), form.getEndTime()));
			this.setTotalTimeMinute(totalTimeMinute(form));
		}
	}
}