package com.example.demo.login.domain.model;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ChangeContractTimeForm {

	private int userId;

	private int cintractId;

	@NotNull
	private Integer newContractTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newStartTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newBreakTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime newEndTime;
}