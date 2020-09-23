package com.example.demo.login.domain.repository;

import org.springframework.dao.DataAccessException;
import com.example.demo.login.domain.model.Contract;

public interface ContractDao {
	
	// Contractテーブルに1件insert
	public int insertOne(Contract contract) throws DataAccessException;
	
	// Contractテーブルからデータ取得
	public Contract selectOne() throws DataAccessException;
	
	
}