package com.example.demo.login.domain.model;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class WorkTime {	
	
	private int userId;
	private LocalDate workDay;
	private LocalDateTime startTime;
	private LocalTime ldtBreakTime;
	private LocalDateTime endTime;
	
	
	public void setWorkTime(WorkTimeForm form) {
				
		this.setWorkDay(form.getWorkDay());
		
		this.setStartTime(LocalDateTime.of(form.getWorkDay(), form.getStartTime()));
		
		this.setLdtBreakTime(form.getBreakTime());
				
		
		if (form.isOverTimeFlag()) {
			
			this.setEndTime(LocalDateTime.of(this.getWorkDay().plusDays(1), form.getEndTime()));
			
		} else {
			
			this.setEndTime(LocalDateTime.of(form.getWorkDay(), form.getEndTime()));
		} 
	}
}