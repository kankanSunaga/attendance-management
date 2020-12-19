package com.example.demo.login.domain.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.repository.UserDao;

@Service
public class ForgotPasswordService {

	@Autowired
	MailSender mailSender;

	@Autowired
	UserDao userDao;

	@Async
	public void sendMail(String email) throws IOException {

		boolean existEmail = userDao.findByEmail(email).isPresent();

		if (existEmail) {
			SimpleMailMessage msg = new SimpleMailMessage();

			msg.setTo(email);
			msg.setBcc("attendancemanagement.system.2020@gmail.com");

			Path mailDir = Paths.get(new ClassPathResource("mail").getURI());
			Path mailFile = mailDir.resolve("mail.txt");
			String text = String.join("\n", Files.readAllLines(mailFile));

			msg.setSubject("from Spring System Mail");
			msg.setText(text);
			mailSender.send(msg);
		}
	}
}