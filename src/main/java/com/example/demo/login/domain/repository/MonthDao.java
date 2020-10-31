package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;


 public interface MonthDao {

 	public int updateToDeadline(int year, int month) throws DataAccessException;
 	
 	public List<User> getRequestUsers() throws DataAccessException;
 } 
 