package com.cg.bank.service;

import java.sql.Date;
import java.util.List;

import com.cg.bank.entities.Transactions;

public interface PassbookMaintenanceService {

	public List<Transactions> updatePassbook(String accountId);
	
	public void updatelastUpdated(String accountId);

	public List<Transactions> accountSummary(String accountId, Date startDate, Date endDate);

	boolean accountValidation(String accountId);

}
