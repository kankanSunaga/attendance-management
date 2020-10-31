package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;


 public interface MonthDao {

 	// 締め切りステータスを変更する
 	public int updateToDeadline(int year, int month) throws DataAccessException;
 	
 	public List<User> getRequestUsers() throws DataAccessException;
 } 
 