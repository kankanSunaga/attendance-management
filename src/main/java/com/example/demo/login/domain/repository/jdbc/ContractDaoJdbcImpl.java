package com.example.demo.login.domain.repository.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.repository.ContractDao;


@Repository
public class ContractDaoJdbcImpl implements ContractDao {

	@Autowired
	JdbcTemplate jdbc;

	@Override
	public void insertOne(Contract contract) {
		jdbc.update("INSERT INTO contract"
				+ " (contractTime, startTime, breakTime, endTime, startDate, officeName, userId)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)",
				contract.getContractTime(), contract.getStartTime(), contract.getBreakTime(), contract.getEndTime(),
				contract.getStartDate(), contract.getOfficeName(), contract.getUserId());
	}

	public Contract activeSelectOne(int contractId) {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM contract WHERE contractId= ?", contractId);

		Contract contract = new Contract();
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

	public List<Contract> selectMany(int userId) {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM contract"
				+ " WHERE userId = ? ORDER BY startDate DESC", userId);

		List<Contract> contractList = new ArrayList<>();
		for (Map<String, Object> map : getList) {
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

			contractList.add(contract);
		}
		return contractList;
	}

	public List<Contract> selectByUserId(int userId) {
		List<Map<String, Object>> list = jdbc.queryForList("SELECT * FROM contract WHERE userId =?", userId);

		return list.stream().map(contractMap -> this.mapToContract(contractMap)).collect(Collectors.toList());
	}

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

	public Contract latestContract(int userId) {
		Map<String, Object> map = jdbc.queryForMap("SELECT contract.* FROM user"
				+ " INNER JOIN contract ON user.userId = contract.userId"
				+ " INNER JOIN month ON contract.contractId = month.contractId"
				+ " WHERE user.userId = ?"
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

	public Contract underContract(int userId, LocalDate nowDate) {
		Map<String, Object> map = jdbc.queryForMap("SELECT contract.* FROM user"
				+ " INNER JOIN contract ON user.userId = contract.userId"
				+ " WHERE contract.startDate <= '" + nowDate.toString() + "' AND" + " (contract.endDate >= '"
				+ nowDate.toString() + "' OR contract.endDate IS NOT NULL)" + " AND user.userId = ?", userId);

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

	public int updateContract(Contract contract) {
		java.sql.Time startTime = java.sql.Time.valueOf(contract.getStartTime());
		java.sql.Time breakTime = java.sql.Time.valueOf(contract.getBreakTime());
		java.sql.Time endTime = java.sql.Time.valueOf(contract.getEndTime());

		int rowNumber = jdbc.update("UPDATE contract"
				+ " SET contractTime = ?, startTime = ?, breakTime = ?, endTime = ? WHERE userId = ?"
				+ " ORDER BY contractId DESC LIMIT 1",
				contract.getContractTime(), startTime, breakTime, endTime, contract.getUserId());

		return rowNumber;
	}

	public void updateEndDate(Contract contract) {
		jdbc.update("UPDATE contract SET endDate=? WHERE contractId = ?", contract.getEndDate(),contract.getContractId());
	}
}
