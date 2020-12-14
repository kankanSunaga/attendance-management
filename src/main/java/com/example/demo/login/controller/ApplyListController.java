package com.example.demo.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserService;

@Controller
public class ApplyListController {

	@Autowired
	UserService userService;

	@Autowired
	MonthService monthService;

	@GetMapping("/applyList")
	public String getApplyList(Model model) {

		model.addAttribute("userList", userService.selectPermission());

		return "admin/applyList";
	}

	@GetMapping("/changeApplyList")
	public String getChangeApplyList(Model model) {

		model.addAttribute("requestUserList", monthService.getRequestUsers());

		return "admin/changeApplyList";

	}

	@GetMapping("/applyDetail/{userId}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model, @PathVariable("userId") int userId) {

		model.addAttribute("user", userService.selectOne(userId));
		model.addAttribute("contents", "admin/applyDetail::admin_contents");

		return "admin/applyDetail";
	}

	@PostMapping("/applyUserPermission/{userId}")
	public String postPermissionUpdate(Model model, @PathVariable("userId") int userId) {

		userService.updatePermission(userId);

		return getApplyList(model);
	}

	@PostMapping("/applyUserFrozen/{userId}")
	public String postupdateFrozen(Model model, @PathVariable("userId") int userId) {

		userService.updateFrozen(userId);

		return getApplyList(model);
	}
}