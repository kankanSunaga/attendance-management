package com.example.demo.login.domain.model;

import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChangeContractTimeForm {

	private int userId;

	private int cintractId;

	private Integer newContractTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newStartTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newBreakTime;

	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newEndTime;
}