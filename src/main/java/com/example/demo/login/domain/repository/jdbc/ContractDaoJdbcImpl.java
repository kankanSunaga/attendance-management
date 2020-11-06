package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.repository.ContractDao;

@Repository
public class ContractDaoJdbcImpl implements ContractDao {

	@Autowired
	JdbcTemplate jdbc;

	// Contractテーブルの件数を取得
	@Override
	public int insertOne(Contract contract) throws DataAccessException {

		int rowNumber = jdbc.update(
				"INSERT INTO contract(contractTime," + " startTime," + " breakTime," + " endTime," + " startDate,"
						+ " officeName)" + " VALUES(?, ?, ?, ?, ?, ?)",
				contract.getContractTime(), contract.getStartTime(), contract.getBreakTime(), contract.getEndTime(),
				contract.getStartDate(), contract.getOfficeName());

		return rowNumber;
	}

	// Contractテーブルから1件データを取得（動的）
	public Contract activeSelectOne(int contractId) throws DataAccessException {
		// 1件取得
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM contract WHERE contractId= ?", contractId);

		// 結果返却用の変数
		Contract contract = new Contract();

		// 取得したデータを結果返却用の変数にセットしていく
		contract.setContractId((int) map.get("contractId"));
		contract.setContractTime((int) map.get("contractTime"));
		contract.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
		contract.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
		contract.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
		contract.setStartDate(((java.sql.Date) map.get("startDate")).toLocalDate());
		contract.setOfficeName((String) map.get("officeName"));
		contract.setEndDate(((java.sql.Date) map.get("endDate")).toLocalDate());

		return contract;

	}

	// Contractテーブルから全件データを取得（userIdでソート）
	public List<Contract> selectMany(int userId) throws DataAccessException {
		List<Map<String, Object>> getList = jdbc
				.queryForList("SELECT * FROM contract WHERE userId = ? ORDER BY startDate DESC", userId);
		List<Contract> contractList = new ArrayList<>();

		for (Map<String, Object> map : getList) {
			// 結果返却用の変数
			Contract contract = new Contract();

			// 取得したデータを結果返却用の変数にセットしていく
			contract.setContractId((int) map.get("contractId"));
			contract.setContractTime((int) map.get("contractTime"));
			contract.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
			contract.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
			contract.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
			contract.setStartDate(((java.sql.Date) map.get("startDate")).toLocalDate());
			contract.setOfficeName((String) map.get("officeName"));
			contract.setEndDate(((java.sql.Date) map.get("endDate")).toLocalDate());
			contract.setUserId((int) map.get("userId"));

			contractList.add(contract);
		}
		return contractList;
	}

	// userIdをキーに勤務情報を取得する
	public List<Contract> selectByUserId(int userId) throws DataAccessException {
		List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM contract WHERE userId =?", userId);

		return list.stream().map(contractMap -> this.mapToContract(contractMap)).collect(Collectors.toList());
	}

	// contractテーブルから取得した値をセットする
	public Contract mapToContract(Map<String, Object> map) {

		Contract contract = new Contract();

		contract.setContractId((int) map.get("contractId"));
		contract.setContractTime((int) map.get("contractTime"));
		contract.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
		contract.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
		contract.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
		contract.setStartDate(((java.sql.Date) map.get("startDate")).toLocalDate());
		contract.setOfficeName((String) map.get("officeName"));
		contract.setEndDate(((java.sql.Date) map.get("endDate")).toLocalDate());
		contract.setUserId((int) map.get("userId"));

		return contract;
	}

	public Contract latestContract(int userId) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT contract.* FROM user"
						+ " INNER JOIN contract ON user.userId = contract.userId"
						+ " INNER JOIN month ON contract.contractId = month.contractId"
						+ " WHERE user.userId = ? "
						+ " ORDER BY contractId DESC LIMIT 1", userId);

		Contract latestContract = new Contract();

		latestContract.setContractId((int) map.get("contractId"));
		latestContract.setContractTime((int) map.get("contractTime"));
		latestContract.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
		latestContract.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
		latestContract.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
		latestContract.setStartDate(((java.sql.Date) map.get("startDate")).toLocalDate());
		latestContract.setOfficeName((String) map.get("officeName"));
		latestContract.setEndDate(((java.sql.Date) map.get("endDate")).toLocalDate());
		latestContract.setUserId((int) map.get("userId"));

		return latestContract;
	}
}