package com.example.demo.login.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.ChangeEmailForm;
import com.example.demo.login.domain.model.User;
import com.example.demo.login.domain.repository.UserDao;

@Service
public class UserService {
	@Autowired
	UserDao dao;

	@Autowired
	PasswordEncoder passwordEncoder;

	public void insert(User user) {

		dao.insertOne(user);
	}

	public int countPermission() {

		return dao.countPermission();
	}

	public List<User> selectPermission() {

		return dao.selectPermission();
	}

	public User selectOne(int userId) {

		return dao.selectOne(userId);
	}

	public void updatePermission(int userId) {

		dao.updatePermission(userId);
	}

	public void updateFrozen(int userId) {

		dao.updateFrozen(userId);
	}

	public User selectByEmail(String email) {

		return dao.selectByEmail(email);
	}

	public int updateEmail(User user) {

		return dao.updateEmail(user);
	}

	public boolean updatePassword(int userId, String oldPassword, String newPassword) {

		String encodedPassword = dao.selectOne(userId).getPassword();

		boolean status = false;
		if (passwordEncoder.matches(oldPassword, encodedPassword)) {
			User user = selectOne(userId);
			dao.updatePassword(user, newPassword);
			status = true;
		}
		return status;
	}

	public User setNewEmail(User user, ChangeEmailForm form) {

		user.setEmail(form.getNewEmail());

		return user;
	}
}