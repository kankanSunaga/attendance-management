package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.MonthDao;
import com.example.demo.login.domain.service.DateTimeUtilityService;
import com.example.demo.login.domain.model.Month;

@Repository
public class MonthDaoJdbcImpl implements MonthDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Autowired
	DateTimeUtilityService dateTimeUtilityService;
	

	@Override
	public int updateToDeadline(int year, int month) throws DataAccessException {

		int date = jdbc.update("UPDATE month SET deadlineStatus = 'TRUE' WHERE year = ? AND month = ? ", year, month);

		return date;
	}

	@Override
	public List<User> getRequestUsers() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT * FROM user INNER JOIN contract ON user.userId = contract.userId INNER JOIN month ON contract.contractId=month.contractId WHERE requestStatus = 'true'");

		List<User> RuquestUserList = new ArrayList<>();

		for (Map<String, Object> map : getList) {
			User user = new User();

			user.setUserName((String) map.get("UserName"));

			RuquestUserList.add(user);
		}
		return RuquestUserList;
	}

	public Month latestMonth(int userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT month.* FROM user"
						+ " INNER JOIN contract ON user.userId = contract.userId"
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
	
	public Month selectMonthTable(int userId, int contractId, String yearMonth) throws DataAccessException {

		int year = dateTimeUtilityService.getYearAndMonth(yearMonth).get("year");
		int month = dateTimeUtilityService.getYearAndMonth(yearMonth).get("month");

		Map<String, Object> map = jdbc.queryForMap("SELECT month.* FROM user"
						+ " INNER JOIN contract ON user.userId = contract.userId"
						+ " INNER JOIN month ON contract.contractId = month.contractId"
						+ " WHERE user.userId = ? AND contract.contractId = ? AND month.year = ? AND month.month = ?", userId, contractId, year, month);

		Month selectMonth = new Month();

		selectMonth.setMonthId((int) map.get("monthId"));
		selectMonth.setYear((int) map.get("year"));
		selectMonth.setMonth((int) map.get("month"));
		selectMonth.setDeadlineStatus((boolean) map.get("deadlineStatus"));
		selectMonth.setRequestStatus((boolean) map.get("requestStatus"));
		selectMonth.setContractId((int) map.get("contractId"));

		return selectMonth;
	}
}