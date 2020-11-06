package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.Month;

public interface MonthDao {

	public int updateToDeadline(int year, int month) throws DataAccessException;

	public List<User> getRequestUsers() throws DataAccessException;

	public Month selectMonthTable(int userId, int contractId, int monthId) throws DataAccessException;
	
	public Month latestMonth(int userId) throws DataAccessException;
	
	public Month selectOneMonthTable(int userId, int contractId, String yearMonth) throws DataAccessException;
}
