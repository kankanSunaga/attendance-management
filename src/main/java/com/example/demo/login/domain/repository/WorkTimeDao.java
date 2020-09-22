package com.example.demo.login.domain.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.WorkTime;

public interface WorkTimeDao {
	
	//Userテーブルにデータを1件insert
	public int insertOne(WorkTime workTime) throws DataAccessException;
	
	//workTimeテーブルから今月のデータを取得
	public List<WorkTime> selectMonthData() throws DataAccessException;
}