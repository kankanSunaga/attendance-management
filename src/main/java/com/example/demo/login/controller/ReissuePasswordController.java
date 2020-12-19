package com.example.demo.login.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.login.domain.model.ForgotPasswordForm;
import com.example.demo.login.domain.service.util.SessionUtil;



@Controller
public class ReissuePasswordController {
	
	@Autowired
	SessionUtil sessionUtil;
	

	@RequestMapping(value = "/reissuePassword", method = RequestMethod.GET)
	@GetMapping
	public String getReissuePassword(ForgotPasswordForm form, HttpServletRequest request, Model model) throws IOException {
		
		sessionUtil.getUserId(request);
		
		return "login/reissuePassword";
	}
	
	

}
