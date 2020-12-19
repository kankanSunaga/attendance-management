package com.example.demo.login.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.demo.login.domain.model.WorkTime;

public interface WorkTimeDao {

	public void insertOne(WorkTime workTime) throws DataAccessException;

	public void updateOne(WorkTime workTime) throws DataAccessException;

	public List<WorkTime> selectMonthData(int userId) throws DataAccessException;

	public List<WorkTime> selectMany(int contractId) throws DataAccessException;

	public List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay) throws DataAccessException;

	public void deleteOne(int workTimeId) throws DataAccessException;

	public boolean hasExist(WorkTime workTime) throws DataAccessException;
}