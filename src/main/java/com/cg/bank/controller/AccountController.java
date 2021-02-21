package com.cg.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bank.entities.Customer;
import com.cg.bank.exceptions.IdAlreadyExistsException;
import com.cg.bank.exceptions.IdNotFoundException;
import com.cg.bank.service.AccountManagementService;

@RestController
@RequestMapping("/AccountManagement")

public class AccountController {

	@Autowired
	private AccountManagementService service;

	@PostMapping("/create")
	public ResponseEntity<String> addAccount(@RequestBody Customer customer ) throws IdAlreadyExistsException{
		if(customer.getAadharNumber().isEmpty()&&customer.getAddress().isEmpty()&&customer.getContactNumber().toString().isEmpty()&&customer.getCustomerName().isEmpty()&&customer.getGender().isEmpty()&&customer.getPanNumber().isEmpty()&&customer.getUserName().isEmpty()
				&&customer.getAccount().getAccountId().isEmpty()&&customer.getAccount().getAccountType().isEmpty()&&customer.getAccount().getBranch().isEmpty()&&customer.getAccount().getAmount()==0&&customer.getAccount().getLastUpdated().toString().isEmpty()){
			throw new RuntimeException("Please enter all details!");
		}
		else {
			Customer customer1=service.findByAccountId(customer.getAccount().getAccountId());
			if(customer1!=null)
			{
				throw new IdAlreadyExistsException("Account ID already exists");
			}
			else
			{
				service.addAccount(customer);
				ResponseEntity<String> responseEntity = new ResponseEntity<String>("Account created successfully",HttpStatus.OK);
				return responseEntity;
			}
		}
	}

	@GetMapping("/find/{accountId}")
	public Customer findByAccountId(@PathVariable String accountId)  {
		return service.findByAccountId(accountId);
	}

	@PutMapping("/update/{account_Id}/{customerName}/{contactNumber}/{address}")
	public ResponseEntity<String> updateAccount(@PathVariable("account_Id")String accountId,@PathVariable("customerName")String customerName,
			@PathVariable("contactNumber")String contactNumber,@PathVariable("address")String address) throws IdNotFoundException {

		Customer customer=service.findByAccountId(accountId);
		if(customer==null) {
			throw new IdNotFoundException("Plese enter Valid account Id");
		}
		else {
			ResponseEntity<String> rs=  new ResponseEntity<String>(service.updateAccount(accountId, customerName, contactNumber, address),HttpStatus.OK);
			return rs;
		}	
	}

	@DeleteMapping("/delete/{accountId}")
	public ResponseEntity<String> deleteAccount(@PathVariable("accountId") String accountId) throws IdNotFoundException 
	{
		Customer customer=service.findByAccountId(accountId);
		if(customer==null) {
			throw new IdNotFoundException("AccountId does not exists!");
		}
		else {
			ResponseEntity<String> rs =  new ResponseEntity<String>(service.deleteAccount(accountId),HttpStatus.OK);
			return rs;
		}
	}
}