package com.example.demo.login.domain.repository.jdbc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;


@Repository
public class UserDaoJdbcImpl implements UserDao {
	@Autowired
	JdbcTemplate jdbc;
	
	//Userテーブルにデータを1件insert
	@Override
	public int insertOne(User user) throws DataAccessException {
		
		//1件登録
		int rowNumber = jdbc.update("INSERT INTO m_user(user_name,"
				+ " email,"
				+ " password,"
				+ " role)"
				+ " VALUES(?, ?, ?, ?)"
				, user.getUserName()
				, user.getEmail()
				, user.getPassword()
				, user.getRole());
		 
		return rowNumber;
		
	}

}