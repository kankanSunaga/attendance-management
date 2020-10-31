package com.example.demo.login.domain.repository.jdbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Month;
import com.example.demo.login.domain.repository.MonthDao;

@Repository
public class MonthDaoJdbcImpl implements MonthDao {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public int updateToDeadline(int year, int month) throws DataAccessException {

		int date = jdbc.update("UPDATE month SET deadlineStatus = 'TRUE' WHERE year = ? AND month = ? ", year, month);

		return date;

	}

	@Override
	public Month selectMonthTable(int userId, int contractId, int monthId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap(
				"SELECT * FROM user INNER JOIN contract ON user.userId = contract.userId INNER JOIN month ON contract.contractId = month.contractId WHERE user.userId = ? AND contract.contractId = ? AND month.monthId = ?",
				userId, contractId, monthId);


		Month month = new Month();

		month.setMonthId((int) map.get("monthId"));
		month.setYear((int) map.get("year"));
		month.setMonth((int) map.get("month"));
		month.setDeadlineStatus((boolean) map.get("deadlineStatus"));
		month.setRequestStatus((boolean) map.get("requestStatus"));

		return month;
	}
}