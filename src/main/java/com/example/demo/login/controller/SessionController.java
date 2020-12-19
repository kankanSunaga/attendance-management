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

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		User user = userService.selectByEmail(auth.getName());

		HttpSession session = request.getSession();
		session.setAttribute("userId", user.getUserId());

		if (contractService.hasBeenContract(user.getUserId())) {
			response.sendRedirect("/home");
		} else if (userService.selectOne(user.getUserId()).getRole().equals("ROLE_GENERAL")){
			response.sendRedirect("/contract");
		} else {
			response.sendRedirect("/adminHome");
		}
	}
}