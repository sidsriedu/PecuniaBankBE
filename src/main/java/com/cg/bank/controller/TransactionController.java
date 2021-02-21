package com.cg.bank.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bank.entities.Account;
import com.cg.bank.entities.ChequeTransactions;
import com.cg.bank.entities.SlipTransactions;
import com.cg.bank.exceptions.IdNotFound;
import com.cg.bank.exceptions.InsufficientBalance;
import com.cg.bank.service.TransactionService;

@RestController
@RequestMapping("/transactions")

public class TransactionController {

	@Autowired
	private TransactionService service;

	@PutMapping("/debitUsingSlip")
	public ResponseEntity<String> debitUsingSlip(@RequestBody SlipTransactions Debit) throws IdNotFound, InsufficientBalance  {
		Account account=service.getAccountbyID(Debit.getAccountNo());
		if(Debit.getAccountNo().isEmpty()&&Debit.getTransactionType().isEmpty()&&Debit.getAmount()==0&&Debit.getTransactionDate().toString().isEmpty()) {
			throw new RuntimeException("Please enter all details!");
		}
		else if(account==null) {
			throw new IdNotFound("Please enter valid accountId!");
		}
		else {
			if(account.getAmount()<=Debit.getAmount()) {
				throw new InsufficientBalance("Insufficient account balance");
			}
			else
			{
				ResponseEntity< String> details = new ResponseEntity<String>(service.debitUsingSlip(Debit),HttpStatus.OK);
				return details;
			}
		}
	}

	@PutMapping("/debitUsingCheque")
	public ResponseEntity<String> debitUsingCheque(@RequestBody ChequeTransactions Debit) throws IdNotFound, InsufficientBalance  {
		Account account=service.getAccountbyID(Debit.getPayeeAccountNo());
		if(Debit.getAmount()==0&&Debit.getBankName().isEmpty()&&Debit.getChequeNo().isEmpty()&&Debit.getIfsc().isEmpty()&&Debit.getIssueDate().toString().isEmpty()&&Debit.getPayeeAccountNo().isEmpty()&&Debit.getRecipientAccountNo().isEmpty()&&Debit.getTransactionDate().toString().isEmpty()&&Debit.getTransactionType().isEmpty()) {
			throw new RuntimeException("Please enter all details");
		}
		else if(account==null) {
			throw new IdNotFound("Please enter valid accountId!");
		}
		else {
			if(account.getAmount()<=Debit.getAmount()) {
				throw new InsufficientBalance("Insufficient account balance");
			}
			else
			{
				ResponseEntity< String> details = new ResponseEntity<String>(service.debitUsingCheque(Debit),HttpStatus.OK);
				return details;
			}
		}

	}

	@PutMapping("/creditUsingCheque")
	public ResponseEntity<String> creditUsingCheque(@RequestBody ChequeTransactions credit) throws InsufficientBalance, IdNotFound {
		Account account=service.getAccountbyID(credit.getPayeeAccountNo());
		if(credit.getAmount()==0&&credit.getBankName().isEmpty()&&credit.getChequeNo().isEmpty()&&credit.getIfsc().isEmpty()&&credit.getIssueDate().toString().isEmpty()&&credit.getPayeeAccountNo().isEmpty()&&credit.getRecipientAccountNo().isEmpty()&&credit.getTransactionDate().toString().isEmpty()&&credit.getTransactionType().isEmpty()&&credit.getTransactionID()==0&&credit.getChequeID()==0) {
			throw new RuntimeException("Please enter all details");
		}
		else	 if(account==null) {
			throw new IdNotFound("Please enter valid Payee Account No.!");
		}
		else {
			if(account.getAmount()<=credit.getAmount()) {
				throw new InsufficientBalance("Insufficient account balance in payee account");
			}
			else
			{
				ResponseEntity< String> details = new ResponseEntity<String>(service.creditUsingCheque(credit),HttpStatus.OK);
				return details;
			}
		}
	}

	@PutMapping("/creditUsingSlip")
	public ResponseEntity<String> creditUsingSlip(@RequestBody SlipTransactions credit) throws IdNotFound, InsufficientBalance{
		Account account=service.getAccountbyID(credit.getAccountNo());
		if(credit.getAccountNo().isEmpty()&&credit.getTransactionType().isEmpty()&&credit.getAmount()==0&&credit.getTransactionDate().toString().isEmpty()) {
			throw new RuntimeException("Please enter all details!");
		}
		else if(account==null) {
			throw new IdNotFound("Please enter valid accountId!");
		}
		else {
			ResponseEntity< String> details = new ResponseEntity<String>(service.creditUsingSlip(credit),HttpStatus.OK);
			return details;
		}
	}

	@PutMapping("/updateBalance")
	public ResponseEntity<String> updateBalance(@RequestBody Account balance) {
		ResponseEntity< String> response = new ResponseEntity<String>(service.updateBalance(balance),HttpStatus.OK);
		return response;	
	}

}




