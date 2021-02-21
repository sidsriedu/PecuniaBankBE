package com.cg.bank.exceptions;

public class InvalidCredentialsException extends RuntimeException {
	
	public InvalidCredentialsException(String msg) {
		super(msg);
	}

}
