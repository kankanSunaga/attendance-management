package com.example.demo.login.domain.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.login.domain.model.WorkTime;


public interface WorkTimeDao {

	void insertOne(WorkTime workTime);

	void updateOne(WorkTime workTime);

	List<WorkTime> selectMonthData(int userId);

	List<WorkTime> selectMany(int contractId);

	List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay);

	void deleteOne(int workTimeId);

	boolean hasExist(WorkTime workTime);	
}