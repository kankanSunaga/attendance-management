package com.example.demo.login.domain.repository.jdbc;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.SignupDao;

@Repository
public class SignupDaoJdbcImpl implements SignupDao {

	@Autowired
	JdbcTemplate jdbc;

	public boolean hasExist(User user) {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user WHERE email=?", user.getEmail());

		return getList.isEmpty();
	}
}