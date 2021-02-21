package com.cg.bank.service;

import com.cg.bank.entities.Employee;

public interface IEmployeeService {
	
	public Employee addEmployee(Employee employee);
	
	public boolean employeeLogin(String username,String Password);
	
	public Employee changePassword(String username,String oldPassword,String newPassword);
	
	public Employee forgotPassword(Employee employee);
	
	public Employee getEmployee(String username);
	
}
