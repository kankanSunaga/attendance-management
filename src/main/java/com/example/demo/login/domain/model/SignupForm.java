package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class SignupForm {
	
	private String userName;
	
	private String email;
	
	private String password;
	
	private String confirmPassword;
}