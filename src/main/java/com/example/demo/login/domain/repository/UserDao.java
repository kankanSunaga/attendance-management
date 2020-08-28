package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;

public interface UserDao {
	//Userテーブルの件数を取得
	public int count() throws DataAccessException;
	
	//Userテーブルにデータを1件insert
	public int insertOne(String User) throws DataAccessException;
	
	//Userテーブルのデータを1件取得
	public User selectOne(int userId) throws DataAccessException;
	
	//Userテーブルの全データを取得
	public List<User> selectMany() throws DataAccessException;

	//Userテーブルを1件更新
	public int updateOne(User user) throws DataAccessException;
	
	//Userテーブルを1件削除
	public int delateOne(int userId) throws DataAccessException;
	
}

