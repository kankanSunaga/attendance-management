package com.example.demo.login.domain.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.SignupDao;

@Service
public class SignupService {

	@Autowired
	SignupDao dao;

	public boolean hasExist(User user) {

		return dao.hasExist(user);
	}

	public User setUser(SignupForm form) {

		User user = new User();
		LocalDateTime requestTime = LocalDateTime.now();
		String requestTimeStr = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss").format(requestTime);
		
		user.setUserName(form.getUserName());
		user.setPassword(form.getPassword());
		user.setEmail(form.getEmail());
		user.setRole("ROLE_GENERAL");
		user.setPermission(false);
		user.setFrozen(false);
		user.setRequestedAt(requestTimeStr);

		return user;
	}
}