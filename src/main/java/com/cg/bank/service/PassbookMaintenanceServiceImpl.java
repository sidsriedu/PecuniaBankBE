package com.cg.bank.service;

import java.sql.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.bank.dao.PassbookMaintenanceDAO;
import com.cg.bank.entities.Account;
import com.cg.bank.entities.Transactions;

@Service
@Transactional
public class PassbookMaintenanceServiceImpl implements PassbookMaintenanceService{

	Account account=new Account();
	long millis=System.currentTimeMillis();  
	Date date=new Date(millis); 
	
	@Autowired
	private PassbookMaintenanceDAO dao;

	public List<Transactions> updatePassbook(String accountId) {
		List<Transactions> result=dao.updatePassbook(accountId);
		updatelastUpdated(accountId);
		return result;	
		
	}
	
	public void updatelastUpdated(String accountId) {
		 dao.update(accountId,date);
	}
	
	public List<Transactions> accountSummary(String accountId, Date startDate, Date endDate) {
		return dao.accountSummary(accountId, startDate, endDate);
	}
	
	public boolean accountValidation(String accountId) {
		 account=dao.get(accountId);
		if(account==null) {
			return false;
		}
		else {
			return true;
		}
		
	}


	
	
}
