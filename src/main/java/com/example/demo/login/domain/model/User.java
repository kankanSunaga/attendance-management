package com.example.demo.login.domain.model;

import lombok.Data;

@Data
public class User {
	
	private int userId;
	private String userName;
	private String email;
	private String password;
	private String role;
	private boolean permission;
	private boolean frozen;
	private String requestedAt;
}