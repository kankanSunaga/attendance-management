package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Repository
public class UserDaoJdbcImpl implements UserDao {

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public int insertOne(User user) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update(
				"INSERT INTO user(" + " userName," + " email," + " password," + " role," + " permission," + " frozen)"
						+ " VALUES(?, ?, ?, ?, ?, ?)",
				user.getUserName(), user.getEmail(), password, user.getRole(), user.isPermission(), user.isFrozen());
		
		return rowNumber;
	}

	
	@Override
	public int countPermission() throws DataAccessException {

		int countPermission = jdbc.queryForObject(
				"SELECT COUNT(*) FROM user WHERE permission　='FALSE'　and frozen = 'FALSE'", Integer.class);

		return countPermission;
	}

	
	public List<User> selectPermission() throws DataAccessException {

		List<Map<String, Object>> getList = jdbc
				.queryForList("SELECT * FROM user WHERE permission　= 'FALSE' and frozen = 'FALSE'");

		List<User> userList = new ArrayList<>();

		for (Map<String, Object> map : getList) {
			User user = new User();
			
			user.setUserId((int) map.get("UserId"));
			user.setUserName((String) map.get("UserName"));
			user.setEmail((String) map.get("Email"));
			user.setPassword((String) map.get("Password"));
			user.setRole((String) map.get("Role"));
			user.setPermission((boolean) map.get("Permission"));
			user.setFrozen((boolean) map.get("Frozen"));
			user.setRequestedAt((String) map.get("RequestedAt"));

			userList.add(user);
		}

		return userList;

	}


	public User selectOne(int userId) throws DataAccessException {

		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user WHERE userId= ?", userId);
		
		User user = new User();

		user.setUserId((int) map.get("UserId"));
		user.setUserName((String) map.get("UserName"));
		user.setEmail((String) map.get("Email"));
		user.setPassword((String) map.get("Password"));
		user.setRole((String) map.get("Role"));
		user.setPermission((boolean) map.get("Permission"));
		user.setFrozen((boolean) map.get("Frozen"));
		user.setRequestedAt((String) map.get("RequestedAt"));

		return user;
	}


	@Override
	public int updatePermission(User user) throws DataAccessException {
		int rowNumber = jdbc.update("UPDATE user SET permission = 'TRUE' WHERE userId= ?", user.getUserId());
		return rowNumber;
	}

	
	public int updateFrozen(User user) throws DataAccessException {
		int rowNumber = jdbc.update("UPDATE user SET frozen = 'TRUE' WHERE userId= ?", user.getUserId());
		return rowNumber;
	}

	
	@Override
	public User selectByEmail(String email) throws DataAccessException {
		Map<String, Object> map = jdbc.queryForMap("SELECT * FROM user WHERE email= ?", email);

		User user = new User();

		user.setUserId((int) map.get("UserId"));
		user.setUserName((String) map.get("UserName"));
		user.setEmail((String) map.get("Email"));
		user.setPassword((String) map.get("Password"));
		user.setRole((String) map.get("Role"));
		user.setPermission((boolean) map.get("Permission"));
		user.setFrozen((boolean) map.get("Frozen"));
		user.setRequestedAt((String) map.get("RequestedAt"));

		return user;
	}
	
	@Override
	public int updateEmail(User user) throws DataAccessException {
		int statusNumber = jdbc.update("UPDATE user SET email = ? WHERE userId = ?", user.getEmail(), user.getUserId());
		return statusNumber;
	}
	
	@Override
	public int updatePassword(User user, String newPassword) throws DataAccessException {

		String password = passwordEncoder.encode(user.getPassword());
		int statusNumber = jdbc.update("UPDATE user SET password = ? WHERE userId = ?", password, user.getUserId());
		return statusNumber;
		
	}

}
