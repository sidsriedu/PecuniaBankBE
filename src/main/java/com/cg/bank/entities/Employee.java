package com.cg.bank.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Employee {
	@Id
	private String eUsername;
	private String eName;
	private String ePhoneNumber;
	private String eEmail;
	private String ePassword;
	
	public Employee() {
		super();
	}

	public Employee(String eUsername, String eName, String ePhoneNumber, String eEmail, String ePassword) {
		super();
		this.eUsername = eUsername;
		this.eName = eName;
		this.ePhoneNumber = ePhoneNumber;
		this.eEmail = eEmail;
		this.ePassword = ePassword;
	}

	public String geteUsername() {
		return eUsername;
	}

	public void seteUsername(String eUsername) {
		this.eUsername = eUsername;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getePhoneNumber() {
		return ePhoneNumber;
	}

	public void setePhoneNumber(String ePhoneNumber) {
		this.ePhoneNumber = ePhoneNumber;
	}

	public String geteEmail() {
		return eEmail;
	}

	public void seteEmail(String eEmail) {
		this.eEmail = eEmail;
	}

	public String getePassword() {
		return ePassword;
	}

	public void setePassword(String ePassword) {
		this.ePassword = ePassword;
	}
	
	
	
	
}
