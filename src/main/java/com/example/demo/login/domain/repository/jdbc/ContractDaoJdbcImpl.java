package com.example.demo.login.domain.repository.jdbc;

import java.util.Map;

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
	
	//Contractテーブルの件数を取得
	@Override
	public int insertOne(Contract contract) throws DataAccessException {
		
		int rowNumber = jdbc.update("INSERT INTO contract(contractTime,"
				+ " startTime,"
				+ " breakTime,"
				+ " endTime,"
				+ " startDate,"
				+ " officeName)"
				+ " VALUES(?, ?, ?, ?, ?, ?)"
				, contract.getContractTime()
				, contract.getStartTime()
				, contract.getBreakTime()
				, contract.getEndTime()
				, contract.getStartDate()
				, contract.getOfficeName());
		
		return rowNumber;
	}
	
	//Contractテーブルからデータを取得
  	public  Contract selectOne() throws DataAccessException {
    	//1件取得
		Map<String, Object>map = jdbc.queryForMap("SELECT * FROM contract WHERE contractId=1");
		
    	//結果返却用の変数
		Contract contract = new Contract();
					
		//取得したデータを結果返却用の変数にセットしていく
		contract.setContractId((int)map.get("contractId"));
		contract.setContractTime((int)map.get("contractTime"));
		contract.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
		contract.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
		contract.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
		contract.setStartDate(((java.sql.Date)map.get("startDate")).toLocalDate());
		contract.setOfficeName((String)map.get("officeName"));
		contract.setEndDate(((java.sql.Date)map.get("endDate")).toLocalDate());
		
		return contract;
    	
    }
}