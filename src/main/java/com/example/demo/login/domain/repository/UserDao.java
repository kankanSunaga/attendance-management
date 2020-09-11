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
	
	///Userテーブルから未承認ユーザー１件取得
	public User selectOne(int userId) throws DataAccessException;
	
	///Userテーブルの承認ステータス　未承認→承認に変更
	public int updatePermission(User user) throws DataAccessException;
	
	///Userテーブルの凍結ステータス　false→trueに変更
	public int updateFrozen(User user) throws DataAccessException;
}

