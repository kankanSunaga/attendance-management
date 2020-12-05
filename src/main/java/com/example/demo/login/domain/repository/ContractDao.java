package com.example.demo.login.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;
import com.example.demo.login.domain.model.Contract;

public interface ContractDao {
	
	// Contractテーブルに1件insert
	public void insertOne(Contract contract) throws DataAccessException;
	
	// Contractテーブルからデータ取得（動的）
	public Contract activeSelectOne(int contractId) throws DataAccessException;
	
	// Contractテーブルからデータ全件取得（userIdでソート）
	public List<Contract> selectMany(int userId) throws DataAccessException;
	
	// Contractテーブルから特定のユーザーのデータ取得
	public List<Contract> selectByUserId(int userId) throws DataAccessException;
	
	public Contract latestContract(int userId) throws DataAccessException;
	
	public Contract underContract(int userId, LocalDate today) throws DataAccessException;
	
	public int updateContract(Contract contract) throws DataAccessException;
	
	public void updateEndDate(Contract contract) throws DataAccessException;
	
}