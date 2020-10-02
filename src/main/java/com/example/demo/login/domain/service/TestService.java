package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.TestModel;
import com.example.demo.login.domain.repository.TestDao;

@Service
public class TestService {
	
	@Autowired
	TestDao dao;
	
	public List<TestModel> selectMany() {
		
		return dao.selectMany();
	}
}