package com.example.demo.login.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.WorkTime;

public interface WorkTimeDao {
	
	// userテーブルにデータを1件insert
	public int insertOne(WorkTime workTime) throws DataAccessException;
	
	// workTimeテーブルから今月のデータを取得
	public List<WorkTime> selectMonthData() throws DataAccessException;
	
	// workTimeテーブルから月のデータを全件取得
	public List<WorkTime> selectMany(int contractId) throws DataAccessException;
	
	// workTimeテーブルから月のデータを全件取得（範囲検索）
	public List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay) throws DataAccessException;
		
}