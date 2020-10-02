package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.TestModel;
import com.example.demo.login.domain.repository.TestDao;

@Repository
public class TestJdbc implements TestDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	public List<TestModel> selectMany() throws DataAccessException {
  		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM test");
  		List<TestModel> testList = new ArrayList<>();

  		for(Map<String, Object> map:getList) {
  			
  			//結果返却用の変数
  			TestModel testModel = new TestModel();

  			//取得したデータを結果返却用の変数にセットしていく
  			testModel.setTestName((String)map.get("testName"));
  			testModel.setTestAge((int)map.get("testAge"));
  			testModel.setTestDate(((java.sql.Date)map.get("testDate")).toLocalDate());
  			testModel.setTestTime(((java.sql.Time) map.get("testTime")).toLocalTime());
  			
  			testList.add(testModel);
  		}
  		
  		return testList;
  	}
}