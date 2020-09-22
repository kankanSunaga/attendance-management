package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ContractForm {
	
	@NotNull
	private int contractTime; //契約勤務時間
	
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;//想定始業時
	
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime breakTime; //想定休憩時間
	
	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime; //想定退勤時間
	
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate; //勤務開始日
	
	@NotBlank
	private String officeName; //勤務会社名
	
}