package com.cg.bank.dao;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cg.bank.entities.Account;
import com.cg.bank.entities.Transactions;
@Repository
public interface PassbookMaintenanceDAO extends JpaRepository<Account,String>{

	    //Fetching transactions since lastUpdated date 
		@Query("select t from Transactions t where account_Id=?1 and t.transDate>=(select u.lastUpdated from Account u where account_Id=?1) ")
		List<Transactions> updatePassbook(String accountId);
		
		//updating the lastUpdated date
		@Modifying
		@Query("update Account set lastUpdated=?2 where  account_Id=?1")
		void update(String accountId, Date date);
		
		//Fetching transactions between two dates
		@Query("select t from Transactions t where account_Id=?1 and t.transDate>=?2 and t.transDate<=?3")
		List<Transactions> accountSummary(String accountId, Date startDate,Date endDate);
		
		//Account validation
		@Query("select u from Account u where account_Id=?1")
		Account get(String accountId);
}
