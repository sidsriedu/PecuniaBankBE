package com.cg.bank.exceptions;

@SuppressWarnings("serial")
public class IdNotFound extends Exception{
	public IdNotFound(String errorMsg){
		super(errorMsg);
	}
}
