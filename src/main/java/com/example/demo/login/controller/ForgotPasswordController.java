package com.example.demo.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.ForgotPasswordForm;
import com.example.demo.login.domain.service.CreateMailService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class ForgotPasswordController {

	@Autowired
	CreateMailService createMailService;

	@Autowired
	UserService userService;

	@GetMapping("/forgotPassword")
	public String getFotgotPassword(ForgotPasswordForm form) {

		return "login/forgotPassword";
	}

	@PostMapping("/forgotPassword")
	public String postForgotPassword(@ModelAttribute @Validated ForgotPasswordForm form, BindingResult bindingResult, Model model) throws IOException {
		
		if (bindingResult.hasErrors()) {
			return getFotgotPassword(form);
		}

		String email = form.getEmailByForgotPassword();

		createMailService.sendMail(email);
		model.addAttribute("status", 1);

		return "login/forgotPassword";
	}
}