package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.service.SignupService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class SignupController {

	@Autowired
	UserService userService;

	@Autowired
	SignupService signupService;

	@GetMapping("/signup")
	public String getSignup(@ModelAttribute SignupForm form) {

		return "login/signup";
	}

	@PostMapping("/signup")
	public String postSignup(@ModelAttribute @Validated SignupForm form, BindingResult bindingResult, Model model) {

		if (bindingResult.hasErrors()) {

			return getSignup(form);
		}

		if (signupService.hasExist(signupService.setUser(form))) {
			userService.insert(signupService.setUser(form));
		} else {
			model.addAttribute("useEmail", true);

			return "login/signup";
		}
		return "login/unapproved";
	}
}