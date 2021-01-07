package com.example.demo.login.domain.repository;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.login.domain.model.Contract;


public interface ContractDao {

	void insertOne(Contract contract);

	Contract activeSelectOne(int contractId);

	List<Contract> selectMany(int userId);

	List<Contract> selectByUserId(int userId);

	Contract latestContract(int userId);

	Contract underContract(int userId, LocalDate today);

	int updateContract(Contract contract);

	void updateEndDate(Contract contract);
}