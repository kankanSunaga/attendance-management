package com.example.demo.login.domain.repository;

 import org.springframework.dao.DataAccessException;


import com.example.demo.login.domain.model.Month;



 public interface MonthDao {

 	// 締め切りステータスを変更する
 	public int updateToDeadline(int year, int month) throws DataAccessException;

 	public Month selectMonthTable(int userId, int contractId, int monthId) throws DataAccessException;
 } 
 