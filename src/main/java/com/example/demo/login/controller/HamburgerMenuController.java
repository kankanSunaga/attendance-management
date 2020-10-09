package com.example.demo.login.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.UserService;


@Controller
public class HamburgerMenuController {
	
	@Autowired
	UserService userService;
	
	@GetMapping("/changePassword")
	public String getChangePassword(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
						
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		model.addAttribute("contents","login/changePassword :: changePassword_contents");
		
		return "login/changePassword";
	}
	
	@GetMapping("/changeEmail")
	public String getChangeEmail(Model model, HttpServletRequest request, HttpServletResponse response) {
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
								
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		
		model.addAttribute("contents","login/changePassword :: changePassword_contents");
		model.addAttribute("contents","login/changeEmail :: changeEmail_contents");
		
		return "login/changeEmail";
	}
	
	@GetMapping("/changeContractTime")
	public String getChangeContractTime(@ModelAttribute ContractForm form, Model model, HttpServletRequest request, HttpServletResponse response) {
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
								
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		
		return "login/changeContractTime";
	}
	
	@GetMapping("/changeContract")
	public String getChangeContract(Model model, HttpServletRequest request, HttpServletResponse response) {
		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());
								
		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());
		
		return "login/changeContract";
	}
}