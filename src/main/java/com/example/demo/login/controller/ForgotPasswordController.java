package com.example.demo.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.ForgotPasswordForm;

@Controller
public class ForgotPasswordController {
	
	@GetMapping("/forgotPassword")
	public String getFotgotPassword(ForgotPasswordForm form, Model model) {
		
		return "login/forgotPassword";
	}
	
	@PostMapping("/forgotPassword")
	public String postForgotPassword(@ModelAttribute @Validated ForgotPasswordForm form, BindingResult bindingResult, Model model) {
		
		if (bindingResult.hasErrors()) {
			return getFotgotPassword(form, model);
		}
		
		model.addAttribute("status", true);
		
		return "login/forgotPassword";
	}
	
}
	