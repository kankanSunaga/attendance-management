package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;

import lombok.Data;

@Data
public class ChangeEmailForm {

	private int userId;

	@Email
	private String newEmail;

	@Email
	private String newConfirmEmail;

	@AssertTrue
	private boolean isDataValidOfEmail() {
		return newEmail.equals(newConfirmEmail);
	}
}