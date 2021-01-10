package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import com.example.demo.login.domain.model.ReissuePassword;
import com.example.demo.login.domain.repository.ReissuePasswordDao;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class ReissuePasswordService {
	
	@Autowired
	ReissuePasswordDao reissuePasswordDao;
	
	@Autowired
	UserDao userDao;
	
	public boolean isValidToken(String token) {
		
		return reissuePasswordDao.findByToken(token)
				.map(value -> value.whithInExpirationTime(LocalDateTime.now()))
				.orElse(false);
	}
	
	
	public Optional<Integer> findUserId(String token) {
		return reissuePasswordDao.findByToken(token)
				.map(ReissuePassword::getUserId);
	}
	
	@Transactional
	public void updatePassword(int userId, String reissuePassword) {
		userDao.updateReissuePassword(userId, reissuePassword);
		reissuePasswordDao.updateChanged(userId);
	}
}