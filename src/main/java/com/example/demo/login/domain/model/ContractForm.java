package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ContractForm {

	private int contractTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime breakTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate startDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate endDate;

	private String officeName;

	private int userId;
}