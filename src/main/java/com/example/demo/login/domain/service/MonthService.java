package com.example.demo.login.domain.service;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.repository.MonthDao;

@Service
public class MonthService {

	@Autowired
	MonthDao dao;

	@Autowired
	DayOfWeekService dayOfWeekService;

	@Autowired
	MonthService monthService;

	@Autowired
	WorkTimeService workTimeService;

	public void updateToDeadline() {

		LocalDate lastMonthDate = LocalDate.now().minusMonths(1);
		dao.updateToDeadline(lastMonthDate.getYear(), lastMonthDate.getMonthValue());
	}

	public Month selectMonthTable(int userId, int contractId, int monthId) {
		return dao.selectMonthTable(userId, contractId, monthId);
	}

	public boolean deadlineCheck(int userId, int contractId, int monthId) throws IOException {

		LocalDate nowDate = LocalDate.now();
		LocalDate lastWeekDay = dayOfWeekService.getLastWeekDay(nowDate);
		boolean stetus = monthService.selectMonthTable(userId, contractId, monthId).isDeadlineStatus();

		if (stetus) {
			stetus = false;
		} else {
			if (nowDate.isAfter(lastWeekDay)) {
				stetus = true;
			} else {
				stetus = false;
			}
		}
		return stetus;
	}

}