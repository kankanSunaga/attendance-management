package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;
import com.example.demo.login.domain.model.WorkDetail;

public interface WorkDetailDao {
	
	// WorkDetailテーブルに1件insert
	public int insertOne(WorkDetail workDetail) throws DataAccessException;
	
	//WorkDetailテーブルからデータ取得
	public  WorkDetail selectOne() throws DataAccessException;
	
	
}