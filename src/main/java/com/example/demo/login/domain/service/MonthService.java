package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.MonthDao;

 @Service
 public class MonthService {

 	@Autowired
 	MonthDao dao;

 	public void updateToDeadline() {

 		LocalDate lastMonthDate = LocalDate.now().minusMonths(1);
 		dao.updateToDeadline(lastMonthDate.getYear(), lastMonthDate.getMonthValue());

 	}
 	
 	public List<User> getRequestUsers(){
		return dao.getRequestUsers();
 		
 	}

 } 