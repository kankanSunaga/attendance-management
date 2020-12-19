package com.example.demo.login.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserIconService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class ApplyListController {

	@Autowired
	UserService userService;

	@Autowired
	MonthService monthService;
	
	@Autowired
	UserIconService userIconService;

	@GetMapping("/applyList")
	public String getApplyList(Model model) throws IOException {

		model.addAttribute("userList", userService.selectPermission());
		model.addAttribute("logo", userIconService.uploadLogoImage());
		
		return "admin/applyList";
	}

	@GetMapping("/changeApplyList")
	public String getChangeApplyList(Model model) throws IOException {

		model.addAttribute("requestUserList", monthService.getRequestUsers());
		model.addAttribute("logo", userIconService.uploadLogoImage());
		
		return "admin/changeApplyList";

	}

	@GetMapping("/applyDetail/{userId}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("userId") int userId) throws IOException {

		model.addAttribute("user", userService.selectOne(userId));
		model.addAttribute("contents", "admin/applyDetail::admin_contents");
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return "admin/applyDetail";
	}

	@PostMapping("/applyUserPermission/{userId}")
	public String postPermissionUpdate(Model model, @PathVariable("userId") int userId) throws IOException {

		userService.updatePermission(userId);
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return getApplyList(model);
	}

	@PostMapping("/applyUserFrozen/{userId}")
	public String postupdateFrozen(Model model, @PathVariable("userId") int userId) throws IOException {

		userService.updateFrozen(userId);
		model.addAttribute("logo", userIconService.uploadLogoImage());

		return getApplyList(model);
	}
}