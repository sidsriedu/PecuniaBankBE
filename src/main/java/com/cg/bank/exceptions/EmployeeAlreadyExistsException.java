package com.cg.bank.exceptions;

public class EmployeeAlreadyExistsException extends RuntimeException{
	
	public EmployeeAlreadyExistsException(String msg) {
		super(msg);
	}

}
