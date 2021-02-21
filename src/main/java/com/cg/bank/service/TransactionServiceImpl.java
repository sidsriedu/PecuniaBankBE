package com.cg.bank.service;


import java.sql.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bank.dao.ChequeTransactionsDao;
import com.cg.bank.dao.SlipTransactionsDao;
import com.cg.bank.dao.TransactionsDao;
import com.cg.bank.dao.UpdateBalanceDao;
import com.cg.bank.entities.Account;
import com.cg.bank.entities.ChequeTransactions;
import com.cg.bank.entities.SlipTransactions;
import com.cg.bank.entities.Transactions;
@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	private SlipTransactionsDao slipdao;
	@Autowired
	private UpdateBalanceDao balancedao;
	@Autowired
	private TransactionsDao transacdao;
	@Autowired
	private ChequeTransactionsDao chequedao;


	Transactions transaction=new Transactions();
	Transactions transaction1=new Transactions();

	long millis=System.currentTimeMillis();  
	Date date=new Date(millis); 

	public String debitUsingSlip(SlipTransactions debit)  {

		Account account= getAccountbyID(debit.getAccountNo());

		if(account.getAmount()>=debit.getAmount())
		{
			debit.setTransactionDate(date);
			slipdao.save(debit);


			transaction.setAccountId(debit.getAccountNo());
			transaction.setTransAmount(debit.getAmount());
			transaction.setTransDate(date);
			transaction.setTransFrom(debit.getAccountNo());
			transaction.setTransId(debit.getTransactionID());
			transaction.setTransTo("self");
			transaction.setTransType(debit.getTransactionType());
			transacdao.save(transaction);


			double newbalance=account.getAmount()-debit.getAmount();
			Account updateAccount=new Account();
			updateAccount.setAccountId(debit.getAccountNo());
			updateAccount.setAmount(newbalance);
			updateBalance(updateAccount);
			return "transaction succesfull";	
		}
		else
		{
			return "Insufficient balance";	
		}

	}
	

	public String debitUsingCheque(ChequeTransactions debit) {

		
			Account account= getAccountbyID(debit.getPayeeAccountNo());
			
			if(account.getAmount()>=debit.getAmount())
			{

				debit.setTransactionDate(date);
				debit.setChequeID(getRandomDoubleBetweenRange(200000,29999));
				chequedao.save(debit);
				

				transaction.setAccountId(debit.getPayeeAccountNo());
				transaction.setTransAmount(debit.getAmount());
				transaction.setTransDate(date);
				transaction.setTransFrom(debit.getPayeeAccountNo());
				transaction.setTransId(debit.getTransactionID());
				transaction.setTransTo(debit.getRecipientAccountNo());
				transaction.setTransType(debit.getTransactionType());
				transacdao.save(transaction);
				
				
				double newbalance=account.getAmount()-debit.getAmount();
				Account updateAccount=new Account();
				updateAccount.setAccountId(debit.getPayeeAccountNo());
				updateAccount.setAmount(newbalance);
				updateBalance(updateAccount);	
				return "transaction succesfull";	

			}
			else
			{
				return "Insufficient balance";	
			}
				
	}

	public String creditUsingCheque(ChequeTransactions credit) {

		
		Account payeeaccount= getAccountbyID(credit.getPayeeAccountNo());
		Account recipientaccount= getAccountbyID(credit.getRecipientAccountNo());

		if(payeeaccount.getAmount()>=credit.getAmount())
		{

			credit.setTransactionDate(date);
			credit.setChequeID(getRandomDoubleBetweenRange(200000,299999));
			chequedao.save(credit);

			transaction.setAccountId(credit.getPayeeAccountNo());
			transaction.setTransAmount(credit.getAmount());
			transaction.setTransDate(date);
			transaction.setTransTo(credit.getRecipientAccountNo());
			transaction.setTransFrom(credit.getPayeeAccountNo());
			transaction.setTransType("debit");
			transaction.setTransId(credit.getTransactionID());
			transacdao.save(transaction);
			
			transaction1.setAccountId(credit.getRecipientAccountNo());
			transaction1.setTransAmount(credit.getAmount());
			transaction1.setTransDate(date);
			transaction1.setTransTo(credit.getPayeeAccountNo());
			transaction1.setTransFrom(credit.getRecipientAccountNo());
			transaction1.setTransType("credit");
			transaction1.setTransId(credit.getTransactionID());
			transacdao.save(transaction1);

			double payeeamount=payeeaccount.getAmount();
			double recipientamount=recipientaccount.getAmount();

			double payeenewbalance=payeeamount-credit.getAmount();
			Account updateAccount=new Account();
			updateAccount.setAccountId(credit.getPayeeAccountNo());
			updateAccount.setAmount(payeenewbalance);
			updateBalance(updateAccount);

			double recipientnewbalance=recipientamount+credit.getAmount();
			Account updateAccount1=new Account();
			updateAccount1.setAccountId(credit.getRecipientAccountNo());
			updateAccount1.setAmount(recipientnewbalance);
			updateBalance(updateAccount1);

			return "transaction succesfull ";	
		}
		else
		{
			return "Insufficient balance";	
		}

	}


	public String creditUsingSlip(SlipTransactions credit){

		Account account= getAccountbyID(credit.getAccountNo());
		credit.setTransactionDate(date);
		slipdao.save(credit);


		transaction.setAccountId(credit.getAccountNo());
		transaction.setTransDate(date);
		transaction.setTransAmount(credit.getAmount());
		transaction.setTransFrom(credit.getAccountNo());
		transaction.setTransTo("self");
		transaction.setTransId(credit.getTransactionID());
		transaction.setTransType(credit.getTransactionType());
		transacdao.save(transaction);

		double amount=account.getAmount();
		double payeenewbalance=amount+credit.getAmount();
		Account updateAccount=new Account();
		updateAccount.setAccountId(credit.getAccountNo());
		updateAccount.setAmount(payeenewbalance);
		updateBalance(updateAccount);
		return "transaction succesfull ";
	}

	public String updateBalance(Account balance)  {
		balancedao.update(balance.getAccountId(),balance.getAmount());
		return "updated successfully";
	}

	public Account getAccountbyID(String accountID) {
		return balancedao.getAccountbyID(accountID);
	}
	
	public static int getRandomDoubleBetweenRange(int min, int max){
		int x = (int) ((Math.random()*((max-min)+1))+min);
		return x;
	}
}
