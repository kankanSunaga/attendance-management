package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.TestModel;

public interface TestDao {
	
	public List<TestModel> selectMany() throws DataAccessException;
	
}