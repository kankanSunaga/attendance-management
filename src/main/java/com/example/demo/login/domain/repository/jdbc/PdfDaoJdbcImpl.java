package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.Pdf;
import com.example.demo.login.domain.repository.PdfDao;



@Repository
public class PdfDaoJdbcImpl implements PdfDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	public List<Pdf> selectMany() throws DataAccessException {
  		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM test");
  		List<Pdf> pdfList = new ArrayList<>();

  		for(Map<String, Object> map:getList) {
  			
  			//結果返却用の変数
  			Pdf pdf = new Pdf();

  			//取得したデータを結果返却用の変数にセットしていく
  			pdf.setTestName((String)map.get("testName"));
  			pdf.setTestAge((int)map.get("testAge"));
  			pdf.setTestDate(((java.sql.Date)map.get("testDate")).toLocalDate());
  			pdf.setTestTime(((java.sql.Time) map.get("testTime")).toLocalTime());
  			
  			pdfList.add(pdf);
  		}
  		
  		return pdfList;
  	}
}