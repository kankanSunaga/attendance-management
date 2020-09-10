package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Service
public class WorkTimeService {
	
	@Autowired
	WorkTimeDao dao;
	
	//insert用メソッド
    public boolean insert(WorkTime workTime) {
        
        int rowNumber = dao.insertOne(workTime);
        
        return rowNumber > 0;
    }
}