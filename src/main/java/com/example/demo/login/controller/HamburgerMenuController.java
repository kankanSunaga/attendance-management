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

import com.example.demo.login.domain.model.ChangeContractTimeForm;
import com.example.demo.login.domain.model.ChangeEmailForm;
import com.example.demo.login.domain.model.ChangePasswordForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.UserIconForm;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;
import com.example.demo.login.domain.service.util.SessionUtil;

@Controller
public class HamburgerMenuController {

	@Autowired
	UserService userService;

	@Autowired
	UserIconService userIconService;

	@Autowired
	SessionUtil sessionUtil;
	
	@Autowired
	ContractService contractService;
	
	@Autowired
	SessionUtil sessionutil;

	@Autowired
	HttpServletRequest request;

	@GetMapping("/changeUserIcon")
	public String getChangeUserIcon(Model model) {

		return "login/changeUserIcon";
	}

	@PostMapping("/changeUserIcon")
	public String postChengeUserIcon(UserIconForm form, Model model) throws IOException {

		int userId = sessionUtil.getUserId(request);

		if (form.getFile().isEmpty()) {
			return "login/changeUserIcon";
		}

		model.addAttribute("updateStatus", userIconService.uploadImage(form.getFile(), userId));

		return "login/changeUserIcon";
	}

	@GetMapping("/changePassword")
	public String getChangePassword(@ModelAttribute ChangePasswordForm form, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		User user = userService.selectOne(userId);
		model.addAttribute("user", user);

		return "login/changePassword";

	}

	@PostMapping("/changePassword")
	public String postChengePassword(@ModelAttribute @Validated ChangePasswordForm form, BindingResult bindingResult,
			Model model, HttpServletRequest request) throws IOException {

		if (bindingResult.hasErrors()) {
			return getChangePassword(form, request, model);
		}

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		model.addAttribute("status", userService.updatePassword(userId, form.getPassword(), form.getNewPassword()));

		return "login/changePassword";

	}

	@GetMapping("/changeEmail")
	public String getChangeEmail(@ModelAttribute ChangeEmailForm form, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		User user = userService.selectOne(userId);
		model.addAttribute("user", user);

		return "login/changeEmail";

	}

	@PostMapping("/changeEmail")
	public String postChengeEmail(@ModelAttribute @Validated ChangeEmailForm form, BindingResult bindingResult,
			Model model, HttpServletRequest request) throws IOException {

		if (bindingResult.hasErrors()) {
			return getChangeEmail(form, request, model);
		}

		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		User user = userService.selectOne(userId);
		user.setEmail(form.getNewEmail());
		userService.updateEmail(user);

		return "redirect:/changeEmail";

	}

	
	@GetMapping("/changeContractTime")
	public String getChangeContractTime(@ModelAttribute ChangeContractTimeForm form, Model model, HttpServletRequest request) {
		
		int userId = sessionUtil.getUserId(request);
		
		contractService.setOldContractTime(form, userId);
		 
		model.addAttribute("ChangeContractTimeForm", form);
		
		return "login/changeContractTime";
		
	}
	
	
	@PostMapping("/changeContractTime")
	public String postChangeContractTime(@ModelAttribute @Validated ChangeContractTimeForm form,  BindingResult bindingResult, Model model, HttpServletRequest request) throws IOException {
		
		if (bindingResult.hasErrors()) {
        	return getChangeContractTime(form, model, request);
        }
		
		HttpSession session = request.getSession();
		int userId = (int)session.getAttribute("userId");
		
		model.addAttribute("status", contractService.updateContract(contractService.setUpdateContractTime(form, userId)));
		
		return "login/changeContractTime";
		
	}

	
	@GetMapping("/changeContract")
	public String getChangeContract(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.getAttribute("userId");

		return "login/changeContract";
	}
}