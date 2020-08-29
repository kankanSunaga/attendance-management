package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;

@Controller
public class SignupController {
	
	@GetMapping("/signup")
	public String getSignup(@ModelAttribute SignupForm form, Model model) {
		
		return "login/signup";
	}
	
	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			
			return getSignup(form, model);
		}
		
		System.out.println(form);
		
		return "login/unapproved";
	}
}