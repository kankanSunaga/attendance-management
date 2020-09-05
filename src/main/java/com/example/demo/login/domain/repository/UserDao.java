package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.User;


public interface UserDao {
	
	//Userテーブルにデータを1件insert
	public int insertOne(User user) throws DataAccessException;
	
	//Userテーブルから未承認(false)の数を取得
	public int countPermission() throws DataAccessException;
	
	////Userテーブルから未承認ユーザーのデータを取得
	public List<User> selectPermission() throws DataAccessException;
}

