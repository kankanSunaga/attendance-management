package com.example.demo.login.domain.repository.jdbc;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.MonthDao;
import com.example.demo.login.domain.service.util.DateTimeUtil;
import com.example.demo.login.domain.model.Month;

@Repository
public class MonthDaoJdbcImpl implements MonthDao {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	DateTimeUtil dateTimeUtil;

	@Override
	public int updateToDeadline(int year, int month) {

		int date = jdbc.update("UPDATE month SET deadlineStatus = 'TRUE' WHERE year = ? AND month = ? ", year, month);

		return date;
	}

	@Override
	public List<User> getRequestUsers() {

		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user"
				+ " INNER JOIN contract ON user.userId = contract.userId"
				+ " INNER JOIN month ON contract.contractId=month.contractId"
				+ " WHERE requestStatus = 'true'");

		List<User> RuquestUserList = new ArrayList<>();
		for (Map<String, Object> map : getList) {
			User user = new User();
			user.setUserName((String) map.get("UserName"));

			RuquestUserList.add(user);
		}

		return RuquestUserList;
	}

	public Month latestMonth(int userId) {

		Map<String, Object> map = jdbc
				.queryForMap("SELECT month.* FROM user" + " INNER JOIN contract ON user.userId = contract.userId"
						+ " INNER JOIN month ON contract.contractId = month.contractId"
						+ " WHERE user.userId = ?"
						+ " ORDER BY monthId DESC LIMIT 1", userId);

		Month latestMonth = new Month();
		latestMonth.setMonthId((int) map.get("monthId"));
		latestMonth.setYear((int) map.get("year"));
		latestMonth.setMonth((int) map.get("month"));
		latestMonth.setDeadlineStatus((boolean) map.get("deadlineStatus"));
		latestMonth.setRequestStatus((boolean) map.get("requestStatus"));
		latestMonth.setContractId((int) map.get("contractId"));

		return latestMonth;
	}

	public Month selectMonthTable(int contractId, String yearMonth) {

		int year = dateTimeUtil.getYearAndMonth(yearMonth).get("year");
		int month = dateTimeUtil.getYearAndMonth(yearMonth).get("month");

		Map<String, Object> map = jdbc.queryForMap("SELECT month.* FROM user"
				+ " INNER JOIN contract ON user.userId = contract.userId"
				+ " INNER JOIN month ON contract.contractId = month.contractId"
				+ " WHERE contract.contractId = ? AND month.year = ? AND month.month = ?",
				contractId, year, month);

		Month selectMonth = new Month();
		selectMonth.setMonthId((int) map.get("monthId"));
		selectMonth.setYear((int) map.get("year"));
		selectMonth.setMonth((int) map.get("month"));
		selectMonth.setDeadlineStatus((boolean) map.get("deadlineStatus"));
		selectMonth.setRequestStatus((boolean) map.get("requestStatus"));
		selectMonth.setContractId((int) map.get("contractId"));

		return selectMonth;
	}

	public void insertOne(Month month) {

		jdbc.update("INSERT INTO month (year, month, deadlineStatus, requestStatus, contractId)"
				+ " VALUES(?, ?, ?, ?, ?)",
				month.getYear(), month.getMonth(), false, false, month.getContractId());
	}

	public void update(Month month) {

		jdbc.update("UPDATE month SET monthId=?, year=?, month=?, deadlineStatus=?, requestStatus=?, contractId=?"
				+ " WHERE monthId=?",
				month.getMonthId(), month.getYear(), month.getMonth(), month.isDeadlineStatus(),
				month.isRequestStatus(), month.getContractId(), month.getMonthId());
	}

	public List<Month> getMonthList(int contractId) {

		List<Map<String, Object>> getList = jdbc.queryForList("SELECT month.* FROM contract"
				+ " INNER JOIN month ON contract.contractId = month.contractId"
				+ " WHERE contract.contractId = ?"
				+ " ORDER BY monthId DESC", contractId);

		List<Month> monthList = new ArrayList<>();
		for (Map<String, Object> map : getList) {
			Month month = new Month();
			month.setMonthId((int) map.get("monthId"));
			month.setYear((int) map.get("year"));
			month.setMonth((int) map.get("month"));
			month.setDeadlineStatus((boolean) map.get("deadlineStatus"));
			month.setRequestStatus((boolean) map.get("requestStatus"));
			month.setContractId((int) map.get("contractId"));

			monthList.add(month);
		}

		return monthList;
	}

	public List<WorkTime> getWorkMonth(int contractId, int monthId) {

		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM workTime"
				+ " WHERE contractId=? AND monthId=?" 
				+ " ORDER BY workDay", contractId, monthId);

		List<WorkTime> contractDayList = new ArrayList<>();
		for (Map<String, Object> map : getList) {
			WorkTime workTime = new WorkTime();
			workTime.setWorkTimeId((int) map.get("workTimeId"));
			workTime.setWorkDay(((java.sql.Date) map.get("workDay")).toLocalDate());
			workTime.setStartTime(((Timestamp) map.get("startTime")).toLocalDateTime());
			workTime.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
			workTime.setEndTime(((Timestamp) map.get("endTime")).toLocalDateTime());
			workTime.setWorkTimeMinute((int) map.get("workTimeMinute"));
			workTime.setContractId((int) map.get("contractId"));

			contractDayList.add(workTime);
		}

		return contractDayList;
	}
	
	public List<WorkTime> getMonth(int monthId) {

		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM workTime"
				+ " WHERE monthId=?" 
				+ " ORDER BY workDay", monthId);

		List<WorkTime> contractDayList = new ArrayList<>();
		for (Map<String, Object> map : getList) {
			WorkTime workTime = new WorkTime();
			workTime.setWorkTimeId((int) map.get("workTimeId"));
			workTime.setWorkDay(((java.sql.Date) map.get("workDay")).toLocalDate());
			workTime.setStartTime(((Timestamp) map.get("startTime")).toLocalDateTime());
			workTime.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
			workTime.setEndTime(((Timestamp) map.get("endTime")).toLocalDateTime());
			workTime.setWorkTimeMinute((int) map.get("workTimeMinute"));
			workTime.setContractId((int) map.get("contractId"));

			contractDayList.add(workTime);
		}

		return contractDayList;
	}
}