package com.example.demo.login.domain.service;

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

		user.setUserName(form.getUserName());
		user.setPassword(form.getPassword());
		user.setEmail(form.getEmail());
		user.setRole("ROLE_GENERAL");
		user.setPermission(false);
		user.setFrozen(false);

		return user;
	}
}
