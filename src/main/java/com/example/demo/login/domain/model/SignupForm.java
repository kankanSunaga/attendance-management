package com.example.demo.login.domain.model;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignupForm {
	
	private int userId;//ユーザーID
	
	@NotBlank
	private String userName; // 名前
	
	@NotBlank
	@Email
	private String email; // メールアドレス
	
	@NotBlank
	@Length(min = 8, max = 50)
	@Pattern(regexp = "^[a-zA-Z0-9]+$")
	private String password; // パスワード
	
	@NotBlank
	private String confirmPassword; // パスワード確認
	@AssertTrue
	public boolean isConfirmPassword() {
		return password.equals(confirmPassword);				
	}
	private String role;//一般ユーザーか管理ユーザーか。
	private boolean permission;//承認ステータス
	private boolean frozen;//凍結ステータス
	private String requested_at; //申請日時
}