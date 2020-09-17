package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class WorkDetail {
	
	
	
	private int workDetailId; //詳細勤務ID
	private int contractTime; //契約勤務時間
	private LocalTime startTime;//想定始業時間
	private LocalTime breakTime; //想定休憩時間
	private LocalTime endTime; //想定退社時間
	private LocalDate startDate; //勤務開始日
	private String officeName; //勤務会社名
	
	
	
}