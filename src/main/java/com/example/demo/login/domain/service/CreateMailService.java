package com.example.demo.login.domain.service;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.example.demo.login.domain.model.ReissuePassword;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.ReissuePasswordDao;
import com.example.demo.login.domain.repository.UserDao;


@Service
public class CreateMailService {

	@Autowired
	JavaMailSender javaMailSender;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	ReissuePasswordDao reissuePasswordDao;
	
	private String getEmailBody(String email, String userName, String token) {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setTemplateMode(TemplateMode.HTML);
	    TemplateEngine engine = new TemplateEngine();
	    engine.setTemplateResolver(templateResolver);
	    Context context = new Context();
	    
	    context.setVariable("name", userName);
	    context.setVariable("encodeUrl", "http://localhost:8080/login/reissuePassword?token=" + token);
	    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    return templateEngine.process("mail/mail.txt", context);
	}
	
	@Async
	public void sendMail(String email) throws IOException {
		
		Optional<User> userOpt = userDao.findByEmail(email);
		
		if(userOpt.isPresent()) {
			User user = userOpt.get();
			String token = UUID.randomUUID().toString();
			LocalDateTime expirationTime = LocalDateTime.now().plusHours(2); // 有効期限2時間
			ReissuePassword reissuePassword = new ReissuePassword();
			reissuePassword.setUserId(user.getUserId());
			reissuePassword.setPasswordResetToken(token);
			reissuePassword.setExpirationTime(expirationTime);
			reissuePassword.setChanged(false);	
			
			reissuePasswordDao.insertOne(reissuePassword);
			
			SimpleMailMessage msg = new SimpleMailMessage();
		
			msg.setTo(email);
			msg.setBcc("attendancemanagement.system.2020@gmail.com");
		
			String text = getEmailBody(user.getEmail(), user.getUserName(), token);

			msg.setSubject("from Spring System Mail");
			msg.setText(text);
			
			javaMailSender.send(msg);
		}
	}
}