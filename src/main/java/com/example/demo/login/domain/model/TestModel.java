package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class TestModel {
	
	private String testName;
	private int testAge;
	private LocalDate testDate;
	private LocalTime testTime;
}