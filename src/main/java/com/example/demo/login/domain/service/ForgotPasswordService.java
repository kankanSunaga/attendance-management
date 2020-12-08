package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.ForgotPasswordForm;


@Service
public class ForgotPasswordService {
	
	@Autowired
	MailSender mailSender;
	
	@Autowired
	UserService userService;
	
	public boolean getMailAddress(ForgotPasswordForm form) {
		
		String savedEmail = userService.selectByEmail(form.getEmailByForgotPassword()).getEmail();
		String typeInEmail = form.getEmailByForgotPassword();
		
		return savedEmail.equals(typeInEmail);
	}
	
	
	public void sendMail(ForgotPasswordForm form) throws IOException {
		
		SimpleMailMessage msg = new SimpleMailMessage();
		
		msg.setTo(form.getEmailByForgotPassword());
		msg.setBcc("attendancemanagement.system.2020@gmail.com");
		
		Path mailDir = Paths.get(new ClassPathResource("mail").getURI());
		Path mailFile = mailDir.resolve("mail.txt");
		String text = String.join("\n", Files.readAllLines(mailFile));

		msg.setSubject("from Spring System Mail");
		msg.setText(text);
		mailSender.send(msg);
		
	}
	
}
