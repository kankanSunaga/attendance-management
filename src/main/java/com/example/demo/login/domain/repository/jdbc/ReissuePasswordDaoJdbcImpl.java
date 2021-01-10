package com.example.demo.login.domain.repository.jdbc;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.ReissuePassword;
import com.example.demo.login.domain.repository.ReissuePasswordDao;


@Repository
public class ReissuePasswordDaoJdbcImpl implements ReissuePasswordDao {
	
	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	public void insertOne(ReissuePassword reissuePassword) {
		jdbc.update("INSERT INTO reissuePassword("
				+ " reissuePasswordId, userId, passwordResetToken, expirationTime, changed)"
				+ " VALUES(?, ?, ?, ?, ?)",
				reissuePassword.getReissuePasswordId(), reissuePassword.getUserId(), 
				reissuePassword.getPasswordResetToken(), reissuePassword.getExpirationTime(), reissuePassword.isChanged());		
	}
	
	
	@Override
	public Optional<ReissuePassword> findByToken(String token) {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM reissuePassword WHERE passwordResetToken = ? AND changed = 'FALSE'", token);
		
		ReissuePassword reissuePassword = new ReissuePassword();
		reissuePassword.setReissuePasswordId((int)map.get("reissuePasswordId"));
		reissuePassword.setUserId((int)map.get("userId"));
		reissuePassword.setPasswordResetToken((String)map.get("passwordResetToken"));
		reissuePassword.setExpirationTime(((Timestamp)map.get("expirationTime")).toLocalDateTime());
		reissuePassword.setChanged((boolean)map.get("changed"));
		
		return Optional.of(reissuePassword);
	}
	
	
	@Override
	public void updateChanged(int userIdOpt) {
		jdbc.update("UPDATE reissuePassword SET changed = 'TRUE' WHERE userId = ?", userIdOpt);
	}
}