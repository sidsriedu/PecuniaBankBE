package com.cg.bank.exceptions;

@SuppressWarnings("serial")
public class InsufficientBalance extends Exception {
	public InsufficientBalance(String errorMsg){
		super(errorMsg);
	}
}
