package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.CurrentTime;
import com.example.demo.login.domain.repository.CurrentTimeDao;

@Service
public class CurrentTimeService {
	
	@Autowired
	CurrentTimeDao dao;
	
	public boolean insertByBatch(CurrentTime currentTime) {
		
		int time = dao.insertByBatch(currentTime);
		
		return time > 0;
	}
}


	