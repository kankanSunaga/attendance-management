package com.example.demo.login.domain.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class WorkTimeForm {

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate workDay;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime startTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime breakTime;

	@NotNull
	@DateTimeFormat(pattern = "HH:mm")
	private LocalTime endTime;

	private boolean overTimeFlag;
}