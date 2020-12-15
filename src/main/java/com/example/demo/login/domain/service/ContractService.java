package com.example.demo.login.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.login.domain.model.ChangeContractTimeForm;
import com.example.demo.login.domain.model.Contract;
import com.example.demo.login.domain.model.ContractForm;
import com.example.demo.login.domain.repository.ContractDao;
import com.example.demo.login.domain.service.util.DateTimeUtil;

@Service
public class ContractService {

	@Autowired
	ContractDao dao;

	@Autowired
	MonthService monthService;

	@Autowired
	DateTimeUtil dateTimeUtilityService;

	public void insertOne(Contract contract) {
		dao.insertOne(contract);
	}

	public Contract setInsertOne(ContractForm form, int userId) {

		Contract contract = new Contract();
		contract.setContractTime(form.getContractTime());
		contract.setStartTime(form.getStartTime());
		contract.setBreakTime(form.getBreakTime());
		contract.setEndTime(form.getEndTime());
		contract.setStartDate(form.getStartDate());
		contract.setOfficeName(form.getOfficeName());
		contract.setUserId(userId);

		return contract;
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

	public String selectDisplay(String yearMonth, int userId, int contractId, LocalDate nowDate) {
		boolean deadlineStatus = monthService.selectMonthTable(userId, contractId, yearMonth).isDeadlineStatus();
		boolean requestStatus = monthService.selectMonthTable(userId, contractId, yearMonth).isRequestStatus();

		LocalDate lastMonth = nowDate.minusMonths(1);
		String stringLastMonth = dateTimeUtilityService.toStringDate(lastMonth, "yyyyMM");

		String status;
		if (!(deadlineStatus)) {
			status = "notClose"; // 締め切られていない状態（セレクトボックスの表示）
		} else if (requestStatus) {
			status = "nowRequest"; // 変更申請中の状態（変更申請済みの表示）
		} else if (yearMonth.equals(stringLastMonth)) {
			status = "lastMonth"; // １ヶ月前 && 変更申請を出していない（変更申請ボタンの表示）
		} else {
			status = "hide"; // その他（非表示）
		}

		return status;
	}

	public Contract underContract(int userId) {
		LocalDate today = LocalDate.now();

		return dao.underContract(userId, today);
	}

	public Contract setOldContractTime(ChangeContractTimeForm form, int userId) {

		Contract contract = underContract(userId);

		form.setNewContractTime(contract.getContractTime());
		form.setNewStartTime(contract.getStartTime());
		form.setNewBreakTime(contract.getBreakTime());
		form.setNewEndTime(contract.getEndTime());

		return contract;
	}

	public Contract setUpdateContractTime(ChangeContractTimeForm form, int userId) {

		Contract contract = new Contract();

		contract.setUserId(userId);
		contract.setContractTime(form.getNewContractTime());
		contract.setStartTime(form.getNewStartTime());
		contract.setBreakTime(form.getNewBreakTime());
		contract.setEndTime(form.getNewEndTime());

		return contract;
	}

	public boolean updateContract(Contract contract) {

		int rowNumber = dao.updateContract(contract);
		boolean status = false;
		if (rowNumber > 0) {
			status = true;
		}
		return status;
	}

	public void updateEndDate(Contract contract) {
		dao.updateEndDate(contract);
	}
}