package com.example.demo.login.domain.repository.jdbc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
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

	public int insertOne(User user) {
		String password = passwordEncoder.encode(user.getPassword());

		int rowNumber = jdbc.update(
				"INSERT INTO user(userName, email, password, role, permission, frozen, requestedAt)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)",
				user.getUserName(), user.getEmail(), password, user.getRole(), user.isPermission(), user.isFrozen(), user.getRequestedAt());

		return rowNumber;
	}

	public int countPermission() {
		int countPermission = jdbc.queryForObject("SELECT COUNT(*) FROM user"
				+ "	WHERE permission　='FALSE'　and frozen = 'FALSE'", Integer.class);

		return countPermission;
	}

	public List<User> selectPermission() {
		List<Map<String, Object>> getList = jdbc.queryForList("SELECT * FROM user WHERE permission　= 'FALSE' and frozen = 'FALSE'");

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

	public User selectOne(int userId) {
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

	public void updatePermission(int userId) {
		jdbc.update("UPDATE user SET permission = 'TRUE' WHERE userId= ?", userId);
	}

	public void updateFrozen(int userId) {
		jdbc.update("UPDATE user SET frozen = 'TRUE' WHERE userId= ?", userId);
	}

	public User selectByEmail(String email) {
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

	public int updateEmail(User user) {
		int statusNumber = jdbc.update("UPDATE user SET email = ? WHERE userId = ?", user.getEmail(), user.getUserId());

		return statusNumber;
	}

	public int updatePassword(User user, String newPassword) {
		String password = passwordEncoder.encode(newPassword);
		int statusNumber = jdbc.update("UPDATE user SET password = ? WHERE userId = ?", password, user.getUserId());

		return statusNumber;
	}

	public Optional<User> findByEmail(String email) {
		Map<String, Object> map;
		try {
			map = jdbc.queryForMap("SELECT * FROM user WHERE email= ?", email);
		} catch (EmptyResultDataAccessException ignored) {
			return Optional.empty();
		}

		User user = new User();
		user.setUserId((int) map.get("UserId"));
		user.setUserName((String) map.get("UserName"));
		user.setEmail((String) map.get("Email"));
		user.setPassword((String) map.get("Password"));
		user.setRole((String) map.get("Role"));
		user.setPermission((boolean) map.get("Permission"));
		user.setFrozen((boolean) map.get("Frozen"));
		user.setRequestedAt((String) map.get("RequestedAt"));

		return Optional.of(user);
	}
	
	@Override
	public void updateReissuePassword(int userId, String reissuePassword) {
		String password = passwordEncoder.encode(reissuePassword);
		jdbc.update("UPDATE user SET password = ? WHERE userId = ?", password, userId);
	}
}