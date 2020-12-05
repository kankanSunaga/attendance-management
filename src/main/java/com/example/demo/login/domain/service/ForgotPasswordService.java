package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class ForgotPasswordService {
	
	public static final String LINE_SEPARATOR = System.getProperty("line.separator");
	
	@Autowired
	MailSender mailSender;
	
	public String sendMail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo("");
	    msg.setBcc("");
	    msg.setCc(new String[] {});
	    
	    String insertMessage = "Test from Spring Mail" + LINE_SEPARATOR;
	    insertMessage += "Test from Spring Mail" + LINE_SEPARATOR;

	    msg.setSubject("Test from Spring Mail");
	    msg.setText("Test");
	    mailSender.send(msg);
	     
	    return "index";
	    		
	}
	
}
