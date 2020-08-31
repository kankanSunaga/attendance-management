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

    // Userテーブルにデータを1件insert.
    @Override
    public int insertOne(User user) throws DataAccessException {

        //１件登録
        int rowNumber = jdbc.update("INSERT INTO user(userName,"
                + " email,"
                + " password,"
                + " role,"
                + " permission)"
                + " VALUES(?, ?, ?, ?, ?)",
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole(),
                user.isPermission());

        return rowNumber;
    }
    
  //Userテーブルから未承認の数を取得
    @Override
    public int countPermission() throws DataAccessException{
    	//未承認の数を取得
    	int countPermission =jdbc.queryForObject("SELECT COUNT(*) FROM user WHERE permission　='FALSE'",Integer.class);
		System.out.println(countPermission);
    	return countPermission;
    }
}
