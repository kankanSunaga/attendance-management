package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class Month {

	private int monthId;
	private int year;
	private int month;
	private boolean deadlineStatus;
	private boolean requestStatus;
	private int contractId;
}