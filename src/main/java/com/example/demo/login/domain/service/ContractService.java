package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.repository.ContractDao;

@Service
public class ContractService {
	
	@Autowired
	ContractDao dao;
	
	//insertメソッド
	public boolean insert(Contract contract) {
		int rowNumber = dao.insertOne(contract);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
	
	//Contractテーブルのデータ取得
	public Contract selectOne() {
		return dao.selectOne();
	}
	
	//Contractテーブルのデータ取得
	public Contract activeSelectOne(int contractId) {
		return dao.activeSelectOne(contractId);
	}
	
	//Contractテーブルのデータ全件取得（userIdでソート）
	public List<Contract> selectMany(int userId) {
		return dao.selectMany(userId);
	}
	
	// 契約件数の取得
	public boolean hasBeenContract(int userId) {
		List<Contract> list = dao.selectByUserId(userId);

		return list.size() >= 1;

	}
	
}