package com.example.demo.login.domain.repository.jdbc;

 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.dao.DataAccessException;
 import org.springframework.jdbc.core.JdbcTemplate;
 import org.springframework.stereotype.Repository;

 import com.example.demo.login.domain.repository.MonthDao;

 @Repository
 public class MonthDaoJdbcImpl implements MonthDao {

 	@Autowired
 	JdbcTemplate jdbc;

 	@Override
 	public int updateToDeadline(int year, int month) throws DataAccessException {

 		int date = jdbc.update("UPDATE month SET deadlineStatus = 'TRUE' WHERE year = ? AND month = ? ", year, month);

 		return date;

 	}

 } 