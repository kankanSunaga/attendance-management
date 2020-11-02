package com.example.demo.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.ChangeEmailForm;
import com.example.demo.login.domain.model.ChangePasswordForm;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;


@Controller
public class HamburgerMenuController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/changePassword")
	public String getChangePassword(@ModelAttribute ChangePasswordForm form, HttpServletRequest request, Model model) {
						
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		
		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		
		return "login/changePassword";
		
	}
	
	
	@PostMapping("/changePassword")
	public String postChengePassword(@ModelAttribute @Validated ChangePasswordForm form,  BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (bindingResult.hasErrors()) {
        	return getChangePassword(form, request, model);
        }
		
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		
		User user = userService.selectOne(userId);
		user.setPassword(form.getNewPassword());
		userService.updatePassword(user);
		
		return "redirect:/changePassword";
		
	}
	

	@GetMapping("/changeEmail")
	public String getChangeEmail(@ModelAttribute ChangeEmailForm form, HttpServletRequest request, Model model) {
		
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		
		User user = userService.selectOne(userId);
		model.addAttribute("user", user);
		
		return "login/changeEmail";
		
	}
	
	
	@PostMapping("/changeEmail")
	public String postChengeEmail(@ModelAttribute @Validated ChangeEmailForm form,  BindingResult bindingResult, Model model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		if (bindingResult.hasErrors()) {
        	return getChangeEmail(form, request, model);
        }
		
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		
		User user = userService.selectOne(userId);
		user.setEmail(form.getNewEmail());
		userService.updateEmail(user);
		
		return "redirect:/changeEmail";
		
	}
	
	
	@GetMapping("/changeContractTime")
	public String getChangeContractTime(@ModelAttribute ContractForm form, Model model, HttpServletRequest request) {
								
		HttpSession session = request.getSession();
		session.getAttribute("userId");
		
		return "login/changeContractTime";
	}
	
	
	@GetMapping("/changeContract")
	public String getChangeContract(Model model, HttpServletRequest request) {
								
		HttpSession session = request.getSession();
		session.getAttribute("userId");
		
		return "login/changeContract";
	}
	
}

