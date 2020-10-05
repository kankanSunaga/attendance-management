package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.CurrentTime;


public interface CurrentTimeDao {
	
	public int insertByBatch(CurrentTime currentTime) throws DataAccessException;


}



