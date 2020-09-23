package com.example.demo.login.domain.service;

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
}