package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class Contract {

	private int contractId;
	private int contractTime;
	private LocalTime startTime;
	private LocalTime breakTime;
	private LocalTime endTime;
	private LocalDate startDate;
	private String officeName;
	private LocalDate endDate;
	private int userId;
}