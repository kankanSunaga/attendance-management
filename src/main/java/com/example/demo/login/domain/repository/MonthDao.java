package com.example.demo.login.domain.repository;

 import org.springframework.dao.DataAccessException;


 public interface MonthDao {

 	// 締め切りステータスを変更する
 	public int updateToDeadline(int year, int month) throws DataAccessException;

 } 
 