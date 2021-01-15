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
import org.springframework.validation.annotation.Validated;
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
	public String getReissuePassword(@ModelAttribute ReissuePasswordForm form, @RequestParam String token) {
		
		try {
			reissuePasswordService.isValidToken(form.getToken());
		} catch (EmptyResultDataAccessException ignored) {
			return "login/tokenError";
		}

		form.setToken(token);
		
		return "login/reissuePassword";
	}
		
	
	@PostMapping("/login/reissuePassword")
	public String postReissuePassword(@ModelAttribute @Validated ReissuePasswordForm form, BindingResult bindingResult, String token, Model model) {
		
		try {
			reissuePasswordService.isValidToken(form.getToken());
		} catch (EmptyResultDataAccessException ignored) {
			return "login/tokenError";
		}
		
		if(bindingResult.hasErrors()) {
			return getReissuePassword(form, form.getToken());
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
		
		model.addAttribute("status", reissuePasswordService.updatePassword(userIdOpt.get(), form.getReissuePassword()));
		
		return "login/reissuePassword";
	}

}