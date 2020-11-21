package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ChangeEmailForm {
	
	private int userId;

	@NotBlank
	@Email
	private String newEmail; 
	
	@NotBlank
	@Email
	private String newConfirmEmail;
	
	@AssertTrue
	private boolean isDataValidOfEmail() {
		return newEmail.equals(newConfirmEmail);
	}
	
}
