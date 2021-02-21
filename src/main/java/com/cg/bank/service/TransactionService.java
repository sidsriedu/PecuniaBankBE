package com.cg.bank.service;

import com.cg.bank.entities.Account;
import com.cg.bank.entities.ChequeTransactions;
import com.cg.bank.entities.SlipTransactions;

public interface TransactionService {


	String debitUsingSlip(SlipTransactions debit) ;
	

	String updateBalance(Account balance) ;


	Account getAccountbyID(String accountID);


	String debitUsingCheque(ChequeTransactions debit);


	String creditUsingCheque(ChequeTransactions credit);


	String creditUsingSlip(SlipTransactions credit);

}
