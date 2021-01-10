package com.example.demo.login.domain.repository;

import java.util.List;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.model.Month;


public interface MonthDao {

	int updateToDeadline(int year, int month);

	List<User> getRequestUsers();

	Month latestMonth(int userId);

	Month selectMonthTable(int contractId, String yearMonth);

	void insertOne(Month month);

	void update(Month month);

	List<Month> getMonthList(int contractId);
	
	List<WorkTime> getWorkMonth(int contractId, int monthId);
	
	List<WorkTime> getMonth(int monthId);
}
