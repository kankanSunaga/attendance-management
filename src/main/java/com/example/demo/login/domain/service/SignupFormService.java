package com.example.demo.login.domain.service;

import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;

@Service
public class SignupFormService {

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
