package com.cg.bank.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.bank.entities.SlipTransactions;

@Repository
public interface SlipTransactionsDao extends JpaRepository<SlipTransactions, String> {

	
	
	
}
