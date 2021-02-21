package com.cg.bank.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.bank.entities.ChequeTransactions;

@Repository
public interface ChequeTransactionsDao extends JpaRepository<ChequeTransactions, String> {

	
	
	
}
