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
import com.example.demo.login.domain.service.ForgotPasswordService;
import com.example.demo.login.domain.service.UserService;


@Controller
public class ForgotPasswordController {
	
	@Autowired
	ForgotPasswordService forgotPasswordService;
	
	@Autowired
	UserService userService;
	
	@GetMapping("/forgotPassword")
	public String getFotgotPassword(ForgotPasswordForm form, Model model) {
		
		return "login/forgotPassword";
	}
	
	
	@PostMapping("/forgotPassword")
	public String postForgotPassword(@ModelAttribute @Validated ForgotPasswordForm form, BindingResult bindingResult, Model model, String email) throws IOException {
		
		if (bindingResult.hasErrors()) {
			return getFotgotPassword(form, model);
		}
		

		if(forgotPasswordService.getMailAddress(form)) {
			
			forgotPasswordService.sendMail(form);
			
			model.addAttribute("status", 1);
			
			return "login/forgotPassword";
		}
		
		model.addAttribute("status", 1);
		
		return "login/forgotPassword";
	}
	
}
	