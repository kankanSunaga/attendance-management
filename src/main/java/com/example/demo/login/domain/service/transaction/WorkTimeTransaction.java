package com.example.demo.login.domain.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.login.domain.model.WorkTimeForm;
import com.example.demo.login.domain.service.MonthService;
import com.example.demo.login.domain.service.WorkTimeService;

@Service
public class WorkTimeTransaction {

	@Autowired
	WorkTimeService workTimeService;

	@Autowired
	MonthService monthService;

	@Transactional
	public void insertMonthAndWork(WorkTimeForm form, int userId) {

		monthService.insertOne(monthService.setMonth(userId));

		workTimeService.insertOne(workTimeService.setWorkTime(form, userId));
	}
}