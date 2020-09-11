package com.example.demo.login.domain.model;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkTimeForm {	
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate workDay; // 勤務日
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime; // 始業時間
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime breakTime; // 休憩時間
	
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime; // 終業時間
	
	private boolean overTimeFlag; // チェックボックス
}