package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;


public interface UserDao {
	
	//Userテーブルにデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
	//Userテーブルから未承認(false)の数を取得
	public int countPermission() throws DataAccessException;
	
}

