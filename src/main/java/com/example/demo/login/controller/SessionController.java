package com.example.demo.login.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.service.ContractService;
import com.example.demo.login.domain.service.UserService;

@Controller

public class SessionController {

	@Autowired
	UserService userService;

	@Autowired
	ContractService contractService;

	@RequestMapping("/session")
	public void getSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// SpringSecurityのセッションの呼出(emailの呼出)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		// emailで検索したユーザーのuserIdの取得
		User user = userService.selectByEmail(auth.getName());

		// セッションの保持(userId)
		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());

		// ログイン時に返す画面の決定
		if (contractService.hasBeenContract(user.getUserId())) {
			response.sendRedirect("/home");
		} else {
			response.sendRedirect("/contract");
		}

	}

}