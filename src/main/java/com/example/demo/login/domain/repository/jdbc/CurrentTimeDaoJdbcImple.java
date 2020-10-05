package com.example.demo.login.domain.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.CurrentTime;
import com.example.demo.login.domain.repository.CurrentTimeDao;


@Repository
public class CurrentTimeDaoJdbcImple implements CurrentTimeDao {

	@Autowired
	JdbcTemplate jdbc;
	
	//	 バッチ処理で、今の時間をDBに保存する
	public int insertByBatch(CurrentTime currentTime) throws DataAccessException {
   		int time = jdbc.update("INSERT INTO currentTime( currentTime)" + " VALUES(?))",
   				currentTime.getCurrentTime());
   		return time;
   	}
	
}
	
