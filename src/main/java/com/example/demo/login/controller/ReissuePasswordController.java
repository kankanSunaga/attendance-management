package com.example.demo.login.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.login.domain.model.ReissuePasswordForm;
import com.example.demo.login.domain.service.ReissuePasswordService;
import com.example.demo.login.domain.service.UserService;


@Controller
public class ReissuePasswordController {
	
	@Autowired
	ReissuePasswordService reissuePasswordService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	MessageSource messageSource;
	
	
	@RequestMapping(value = "/login/reissuePassword", method = RequestMethod.GET)
	public String getReissuePassword(@RequestParam String token, ReissuePasswordForm form) {
		
		try {
			reissuePasswordService.isValidToken(form.getToken());
		} catch (EmptyResultDataAccessException ignored) {
			return "login/tokenError";
		}

		form.setToken(token);
		
		return "login/reissuePassword";
	}
		
	
	@PostMapping("/login/reissuePassword")
	public String postReissuePassword(@ModelAttribute ReissuePasswordForm form, BindingResult bindingResult, Model model) {
		
		if(bindingResult.hasErrors()) {
			return "login/reissuePassword";
		}
		
		boolean isValidToken =  reissuePasswordService.isValidToken(form.getToken());
		Optional<Integer> userIdOpt = reissuePasswordService.findUserId(form.getToken());
		
		if(!isValidToken || !userIdOpt.isPresent()) {
			String errorMessage = messageSource.getMessage("tokenError", null, LocaleContextHolder.getLocale());
			FieldError fieldError = new FieldError(bindingResult.getObjectName(), "token", errorMessage);
			bindingResult.addError(fieldError);
			model.addAttribute("status", 0);
			
			return "login/forgotPassword";
		} 
		
		reissuePasswordService.updatePassword(userIdOpt.get(), form.getReissuePassword());
		
		model.addAttribute("status", 1);
		
		return "login/reissuePassword";
	}

}