package com.example.demo.login.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.login.domain.model.SignupForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.UserService;
@Controller
public class ApplyListController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	MonthService monthService;
	
	@GetMapping("/applyList")
	public String getApplyList(Model model){
		
		List<User> userList= userService.selectPermission();
		
		model.addAttribute("userList",userList);
				
		return "admin/applyList";		
	}
	
	@GetMapping("/changeApplyList")
	public String getChangeApplyList(Model model){
		
		List<User> requestUserList= monthService .getRequestUsers();
		
		model.addAttribute("requestUserList",requestUserList);
		
		return "admin/changeApplyList";
		
	}
	
	@GetMapping("/applyDetail/{userId}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model,
			@PathVariable("userId")int userId) {
		model.addAttribute("contents","admin/applyDetail::admin_contents");
		
		User user = userService.selectOne(userId);
		
		form.setUserId(user.getUserId());
		form.setUserName(user.getUserName());
		form.setEmail(user.getEmail());
		form.setPassword(user.getPassword());
		form.setRole(user.getRole());
		form.setPermission(user.isPermission());
		form.setFrozen(user.isFrozen());
		form.setRequestedAt(user.getRequestedAt());
		
		model.addAttribute("signupForm",form);
		
		return "admin/applyDetail";
	}
	@PostMapping("/applyUserPermission/{userId}")
	public String postPermissionUpdate(@ModelAttribute SignupForm form, Model model) {
		
		User user = new User();
		
		user.setUserId(form.getUserId());
		user.setPermission(form.isPermission());
		
		boolean result = userService.updatePermission(user);
		if(result==true) {
			model.addAttribute("result", "更新成功");
		}else {
			model.addAttribute("result", "更新失敗");
		}
		
		return getApplyList(model);
		
	}
	
	@PostMapping("/applyUserFrozen/{userId}")
	public String postupdateFrozen(@ModelAttribute SignupForm form, Model model) {		
		
		User user = new User();
		
		user.setUserId(form.getUserId());
		user.setFrozen(form.isFrozen());
		
		boolean result = userService.updateFrozen(user);
		if(result == true) {
			model.addAttribute("result", "更新成功");
		} else {
			model.addAttribute("result", "更新失敗");
		}
		
		return getApplyList(model);
		
	}
}

