package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.repository.ContractDao;

@Service
public class ContractService {

	@Autowired
	ContractDao dao;

	@Autowired
	MonthService monthService;

	@Autowired
	DateTimeUtilityService dateTimeUtilityService;

	// insertメソッド
	public boolean insert(Contract contract) {
		int rowNumber = dao.insertOne(contract);
		boolean result = false;
		if (rowNumber > 0) {
			result = true;
		}
		return result;
	}

	// Contractテーブルのデータ取得
	public Contract selectOne() {
		return dao.selectOne();
	}

	// Contractテーブルのデータ取得
	public Contract activeSelectOne(int contractId) {
		return dao.activeSelectOne(contractId);
	}

	// Contractテーブルのデータ全件取得（userIdでソート）
	public List<Contract> selectMany(int userId) {
		return dao.selectMany(userId);
	}

	// 契約件数の取得
	public boolean hasBeenContract(int userId) {
		List<Contract> list = dao.selectByUserId(userId);

		return list.size() >= 1;
	}

	public Contract latestContract(int userId) {
		return dao.latestContract(userId);
	}

	public int selectDisplay(String yearMonth, int userId, int contractId) {

		boolean deadlineStatus = monthService.selectMonthTable(userId, contractId, yearMonth).isDeadlineStatus();
		boolean requestStatus = monthService.selectMonthTable(userId, contractId, yearMonth).isRequestStatus();

		LocalDate nowDate = LocalDate.now();
		LocalDate lastMonth = nowDate.minusMonths(1);
		String stringLastMonth = dateTimeUtilityService.toStringDate(lastMonth, "yyyyMM");

		// 1.セレクトボックス表示, 2.申請中ボタン表示, 3.ボタン表示, 0.その他
		int status;
		if (!(deadlineStatus)) {
			status = 1;
		} else if (requestStatus) {
			status = 2;
		} else if (yearMonth.equals(stringLastMonth)) {
			status = 3;
		} else {
			status = 0;
		}

		return status;
	}
}