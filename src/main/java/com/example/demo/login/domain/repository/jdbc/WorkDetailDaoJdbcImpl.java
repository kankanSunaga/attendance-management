package com.example.demo.login.domain.repository.jdbc;

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
}