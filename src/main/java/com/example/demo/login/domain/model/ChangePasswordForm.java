package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ChangePasswordForm {

	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password;

	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String newPassword;

	@NotBlank
	private String newConfirmPassword;

	@AssertTrue(message = "新しいパスワードと新しいパスワード確認が異なります")
	public boolean isDataValidOfPassword() {
		return newPassword.equals(newConfirmPassword);
	}
}