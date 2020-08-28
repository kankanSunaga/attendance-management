package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

public interface UserDao {
	//Userテーブルの件数を取得
	public int count() throws DataAccessException;
	
	//Userテーブルにデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
}

