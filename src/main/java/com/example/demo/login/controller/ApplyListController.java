package com.example.demo.login.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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
	
}

