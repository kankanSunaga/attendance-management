package com.example.demo.login.domain.model;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data

public class SignupForm {
	
	@NotBlank
	private String userName; // 名前
	
	@NotBlank
	@Email
	private String email; // メールアドレス
	
	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password; //パスワード
	
	private String confirmPassword;//パスワード確認
	@AssertTrue
	public boolean isConfirmPassword() {
		if(password == null || password.isEmpty())return true;
		return password.equals(confirmPassword);
	}
}