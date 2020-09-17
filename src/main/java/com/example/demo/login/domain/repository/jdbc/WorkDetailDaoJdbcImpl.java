package com.example.demo.login.domain.repository.jdbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.login.domain.model.WorkDetail;
import com.example.demo.login.domain.repository.WorkDetailDao;

@Repository
public class WorkDetailDaoJdbcImpl implements WorkDetailDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	//WorkDetailテーブルの件数を取得
	@Override
	public int insertOne(WorkDetail workDetail) throws DataAccessException {
		
		int rowNumber = jdbc.update("INSERT INTO workDetail(contractTime,"
				+ " startTime,"
				+ " breakTime,"
				+ " endTime,"
				+ " startDate,"
				+ " officeName)"
				+ " VALUES(?, ?, ?, ?, ?, ?)"
				, workDetail.getContractTime()
				, workDetail.getStartTime()
				, workDetail.getBreakTime()
				, workDetail.getEndTime()
				, workDetail.getStartDate()
				, workDetail.getOfficeName());
		
		return rowNumber;
	}
	
	//workDetailテーブルからデータを取得
  	public  WorkDetail selectOne() throws DataAccessException {
    	//1件取得
		Map<String, Object>map = jdbc.queryForMap("SELECT * FROM workDetail WHERE workDetailId=1");
		
    	//結果返却用の変数
		WorkDetail workDetail = new WorkDetail();
					
		//取得したデータを結果返却用の変数にセットしていく
		workDetail.setWorkDetailId((int)map.get("workDetailId"));
		workDetail.setContractTime((int)map.get("contractTime"));
		workDetail.setStartTime(((java.sql.Time) map.get("startTime")).toLocalTime());
		workDetail.setBreakTime(((java.sql.Time) map.get("breakTime")).toLocalTime());
		workDetail.setEndTime(((java.sql.Time) map.get("endTime")).toLocalTime());
		workDetail.setStartDate(((java.sql.Date)map.get("startDate")).toLocalDate());
		workDetail.setOfficeName((String)map.get("officeName"));
		
		return workDetail;
    	
    }
}