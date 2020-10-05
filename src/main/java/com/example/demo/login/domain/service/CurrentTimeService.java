package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.CurrentTime;
import com.example.demo.login.domain.repository.CurrentTimeDao;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Service
public class CurrentTimeService {
	
	@Autowired
	CurrentTimeDao dao;
	
	public int insertByBatch(CurrentTime currentTime) {
		
		int time = dao.insertByBatch(currentTime);
		
		return time;
	}
}


	