package com.cg.bank.service;

import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bank.dao.AccountManagementDao;
import com.cg.bank.entities.Account;
import com.cg.bank.entities.Customer;

@Service
@Transactional
public class AccountManagementServiceImpl implements AccountManagementService{
	@Autowired
	private AccountManagementDao dao;

	long millis=System.currentTimeMillis();  
	Date date=new Date(millis); 


	public String addAccount(Customer customer) {
		Account account=new Account();
		account=customer.getAccount();
		account.setLastUpdated(date);
		customer.setAccount(account);
		dao.save(customer);
		return "account successfully created";
	}


	public Customer findByAccountId(String accountId){
		return dao.findByAccountId(accountId);
	}


	public String updateAccount(String accountId, String customerName, String contactNumber, String address) {
		System.out.println("ServiceImpl");
		dao.updateAccount(accountId,customerName,contactNumber,address);
		return "successfully updated";
	}


	public String deleteAccount(String accountId){


		dao.deletecustomer(accountId);
		dao.deleteAccount(accountId);
		return "deleted account";

	}
}
