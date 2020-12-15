package com.example.demo.login.domain.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class ForgotPasswordForm {
	
	@Email
	@NotBlank
	private String emailByForgotPassword;
	
	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String reissuePassword;
	
	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String confirmReissuePassword;
	
	@AssertTrue
	public boolean isDataValidOfReissuePassword() {
		if (!reissuePassword.equals(confirmReissuePassword)) {
			return false;
		}
		return true;
		
	}
}