package com.example.demo.login.domain.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
 
import lombok.Data;

@Data
public class ForgotPasswordForm {
	
	@Email
	@NotBlank
	private String emailByForgotPassword;
	
	
}