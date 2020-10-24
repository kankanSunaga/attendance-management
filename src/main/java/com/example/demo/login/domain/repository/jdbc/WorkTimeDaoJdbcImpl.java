package com.example.demo.login.domain.repository.jdbc;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.WorkTime;
import com.example.demo.login.domain.repository.WorkTimeDao;

@Repository
public class WorkTimeDaoJdbcImpl implements WorkTimeDao {

	@Autowired
	JdbcTemplate jdbc;

	// WorkTimeテーブルにデータを1件insert.
	public int insertOne(WorkTime workTime) throws DataAccessException {

		// １件登録
		int job = jdbc.update(
				"INSERT INTO workTime(workDay," + " startTime," + " breakTime," + " endTime," + " workTimeMinute)"
						+ " VALUES(?, ?, ?, ?, ?)",
				workTime.getWorkDay(), workTime.getStartTime(), workTime.getBreakTime(), workTime.getEndTime(),
				workTime.getWorkTimeMinute());

		return job;
	}

	// workTimeテーブルから今月のデータを取得
	public List<WorkTime> selectMonthData() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc
				.queryForList("SELECT * FROM workTime WHERE month(workDay) = month(now())");

		List<WorkTime> monthDataList = new ArrayList<>();

		for (Map<String, Object> map : getList) {
			WorkTime workTime = new WorkTime();
			workTime.setWorkDay(((java.sql.Date) map.get("workDay")).toLocalDate());
			workTime.setStartTime(((Timestamp) map.get("startTime")).toLocalDateTime());
			workTime.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
			workTime.setEndTime(((Timestamp) map.get("endTime")).toLocalDateTime());
			workTime.setWorkTimeMinute((int) map.get("workTimeMinute"));
			monthDataList.add(workTime);
		}
		return monthDataList;
	}

	// workTimeテーブルから月のデータを全件取得
	public List<WorkTime> selectMany(int contractId) throws DataAccessException {

		List<Map<String, Object>> getList = jdbc
				.queryForList("SELECT * FROM workTime WHERE contractId = ? ORDER BY workDay DESC", contractId);

		List<WorkTime> contractMonthList = new ArrayList<>();

		for (Map<String, Object> map : getList) {
			WorkTime workTime = new WorkTime();

			workTime.setWorkTimeId((int) map.get("workTimeId"));
			workTime.setWorkDay(((java.sql.Date) map.get("workDay")).toLocalDate());
			workTime.setStartTime(((Timestamp) map.get("startTime")).toLocalDateTime());
			workTime.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
			workTime.setEndTime(((Timestamp) map.get("endTime")).toLocalDateTime());
			workTime.setWorkTimeMinute((int) map.get("workTimeMinute"));
			workTime.setContractId((int) map.get("contractId"));

			contractMonthList.add(workTime);
		}
		return contractMonthList;
	}

	// workTimeテーブルから月のデータを全件取得（日付で範囲検索）
	public List<WorkTime> rangedSelectMany(int contractId, LocalDate minWorkDay, LocalDate maxWorkDay)
			throws DataAccessException {

		// LocalDateからsql.Dateに型変換（次のqueryでSQLで扱えるようにするため）
		java.sql.Date sqlMinWorkDay = java.sql.Date.valueOf(minWorkDay);
		java.sql.Date sqlMaxWorkDay = java.sql.Date.valueOf(maxWorkDay);

		List<Map<String, Object>> getList = jdbc.queryForList(
				"SELECT * FROM workTime WHERE contractId = ? AND workDay >= ? AND workDay <= ? ORDER BY workDay ASC",
				contractId, sqlMinWorkDay, sqlMaxWorkDay);

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

			// 時間用フォーマット作成
			DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm", Locale.JAPANESE);
			// startTimeのフォーマットを変更
			String stringStartTime = workTime.getStartTime().format(timeFormat);
			workTime.setStringStartTime(stringStartTime);
			// endTimeのフォーマットを変更
			String stringEndTime = workTime.getEndTime().format(timeFormat);
			workTime.setStringEndTime(stringEndTime);
			// ******************** PDF用のフォーマットに変換 ********************

			contractDayList.add(workTime);
		}
		return contractDayList;
	}
}
