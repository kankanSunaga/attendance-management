package com.example.demo.login.domain.service.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

@Service
public class SessionUtil {

	public int getUserId(HttpServletRequest request) {
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute("userId");

		return userId;
	}
}