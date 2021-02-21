package com.cg.bank.util;

import org.springframework.stereotype.Component;

import com.cg.bank.dto.EmployeeDto;

@Component
public class TokenUtil {

	public String generateToken(EmployeeDto credentials)
	{
		System.err.println(credentials);
		StringBuilder usernameSB=new StringBuilder(credentials.geteUsername()+"");
		StringBuilder passwordSB=new StringBuilder(credentials.getePassword());
		String encoded=usernameSB.reverse()+","+passwordSB.reverse();
		return encoded;
	}

	public EmployeeDto decode(String token) {
		String []cred=token.split(",");
		StringBuilder usernameSB=new StringBuilder(cred[0]);
		StringBuilder passwordSB=new StringBuilder(cred[1]);
		String username=usernameSB.reverse().toString();
		String password=passwordSB.reverse().toString();
		EmployeeDto employeeDto = new EmployeeDto();
		employeeDto.seteUsername(username);
		employeeDto.setePassword(password);
		return employeeDto;
		
	}
}