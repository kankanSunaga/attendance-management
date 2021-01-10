package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {

	private int userId;

	private String userName;

	@Email
	private String email;

	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;

	private String confirmPassword;

	@AssertTrue
	public boolean isConfirmPassword() {
		return password.equals(confirmPassword);
	}

	private String role;
	private boolean permission;
	private boolean frozen;
	private String requestedAt;
}