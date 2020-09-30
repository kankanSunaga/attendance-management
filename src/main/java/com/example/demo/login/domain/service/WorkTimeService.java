package com.example.demo.login.domain.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Service
public class WorkTimeService {
	
	@Autowired
	WorkTimeDao dao;
	
	//insert用メソッド
	public boolean insert(WorkTime workTime) {
		
		int rowNumber = dao.insertOne(workTime);		
		
		return rowNumber > 0;
    }
	
	//その月のデータ取得用メソッド
	public List<WorkTime> selectMonthData(){
         return dao.selectMonthData(); 
	}
	
	public List<String> selectCalendar(){	
		// 年月日を設定
		List<String> list = new ArrayList<>();
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		//+１しないと欲しい値が取れないのでしてます。(仕様です)
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = 1;
		// 作成する日数
		int loopCount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH); 
		for(int i = 0;i<loopCount;i++){
			calendar.set(year, month - 1, day+i);
			list.add(calendar.get(Calendar.YEAR) + "/" + (calendar.get(Calendar.MONTH) + 1)+ "/" + calendar.get(Calendar.DATE));			
		}
		return list;
	}
		//月のデータ全件取得用メソッド
		public List<WorkTime> selectMany(int contractId){
	         return dao.selectMany(contractId); 
		}
}
