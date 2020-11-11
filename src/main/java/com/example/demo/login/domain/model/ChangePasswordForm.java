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
	private String newPassword = "";
	
	private String newConfirmPassword = "";
	
	@AssertTrue
	public boolean isDataValid() {
		if (!newPassword.equals(newConfirmPassword)) {
			return false;
		}
    return true;
		
	}
	
}

