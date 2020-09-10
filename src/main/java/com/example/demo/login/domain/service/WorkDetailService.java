package com.example.demo.login.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.WorkDetail;
import com.example.demo.login.domain.repository.WorkDetailDao;

@Service
public class WorkDetailService {
	
	@Autowired
	WorkDetailDao dao;
	
	//insert用メソッド
	public boolean insert(WorkDetail workDetail) {
		int rowNumber = dao.insertOne(workDetail);
		boolean result = false;
		if(rowNumber > 0) {
			result = true;
		}
		return result;
	}
}