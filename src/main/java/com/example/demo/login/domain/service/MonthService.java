package com.example.demo.login.domain.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.model.User;
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


	public List<User> getRequestUsers() {
		return dao.getRequestUsers();

	}
	
	public int ruquestUserCount() {
		
		List<User> ruquestUserList = getRequestUsers();
		
		int ruquestUserCount = ruquestUserList.size();
		
		return ruquestUserCount;
		
	}

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
		boolean stetus = selectMonthTable(userId, contractId, monthId).isDeadlineStatus();

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

	public Month latestMonth(int userId) {
		return dao.latestMonth(userId);
	}
	
	public Month selectOneMonthTable(int userId, int contractId, String yearMonth) {
		return dao.selectOneMonthTable(userId, contractId, yearMonth);
	}
}