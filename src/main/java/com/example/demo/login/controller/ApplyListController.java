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
import com.example.demo.login.domain.service.UserService;
@Controller
public class ApplyListController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/applyList")
	public String getApplyList(Model model){
		//コンテンツ部分に未承認ユーザー一覧を表示する文字列登録
		model.addAttribute("contents","admin/applyList::admin_contents");
		
		//未承認ユーザー一覧の生成
		List<User> userList= userService.selectPermission();
		
		//Modelにユーザーリストを登録
		model.addAttribute("userList",userList);
		
		
		return "admin/applyList";
		
	}
	//動的URLの作成
	@GetMapping("/applyDetail/{userId}")
	public String getUserDetail(@ModelAttribute SignupForm form, Model model,
			@PathVariable("userId")int userId) {
		model.addAttribute("contents","admin/applyDetail::admin_contents");
		
		User user = userService.selectOne(userId);
		
		//Userクラスをフォームクラスに変換
		form.setUserId(user.getUserId());
		form.setUserName(user.getUserName());
		form.setEmail(user.getEmail());
		form.setPassword(user.getPassword());
		form.setRole(user.getRole());
		form.setPermission(user.isPermission());
		form.setFrozen(user.isFrozen());
		form.setRequested_at(user.getRequested_at());
		
		//Modelに登録
		model.addAttribute("signupForm",form);
		
		return "admin/applyDetail";
	}
	@PostMapping("/applyUser/{userId}")
	public String postPermissionUpdate(@ModelAttribute SignupForm form, Model model) {
		System.out.println("許可ボタンの処理");
		
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
	
//	@PostMapping("/applyUser/{userId}")
//	public String postupdateFrozen(@ModelAttribute SignupForm form, Model model) {
//		System.out.println("許可ボタンの処理");
//		
//		User user = new User();
//		
//		user.setUserId(form.getUserId());
//		user.setFrozen(form.isFrozen());
//		
//		boolean result = userService.updateFrozen(user);
//		if(result==true) {
//			model.addAttribute("result", "更新成功");
//		}else {
//			model.addAttribute("result", "更新失敗");
//		}
//		
//		return getApplyList(model);
//		
//	}
}

