package com.example.demo.login.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReissuePassword {
	
	private int reissuePasswordId;
	
	private int userId;
	
	private String passwordResetToken;
	
	private LocalDateTime expirationTime;
	
	private boolean changed = false;
	
	public boolean whithInExpirationTime(LocalDateTime dateTime) {
		return this.expirationTime.isAfter(dateTime);
	}
}