package com.cg.bank.dto;

public class EmployeeDto {
	
	private String eUsername;
	private String eName;
	private String ePhoneNumber;
	private String eEmail;
	private String ePassword;
	private String eOldPassword;
	
	public EmployeeDto() {
		super();
	}

	public EmployeeDto(String eUsername, String eName, String ePhoneNumber, String eEmail, String ePassword, String eOldPassword) {
		super();
		this.eUsername = eUsername;
		this.eName = eName;
		this.ePhoneNumber = ePhoneNumber;
		this.eEmail = eEmail;
		this.ePassword = ePassword;
		this.eOldPassword = eOldPassword;
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

	public String geteOldPassword() {
		return eOldPassword;
	}

	public void seteOldPassword(String eOldPassword) {
		this.eOldPassword = eOldPassword;
	}
	
	

}
