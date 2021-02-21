package com.cg.bank.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cg.bank.dao.EmployeeRepo;
import com.cg.bank.entities.Employee;
import com.cg.bank.exceptions.EmployeeAlreadyExistsException;
import com.cg.bank.exceptions.InvalidCredentialsException;


@Service
public class EmployeeServiceImpl implements IEmployeeService {
	
	@Autowired
	EmployeeRepo employeeRepo;

	public Employee addEmployee(Employee employee) {
		List<Employee> employees = employeeRepo.findAll();
		for(Employee employee1 : employees) {
			if(employee.getePhoneNumber().equals(employee1.getePhoneNumber())
				|| employee.geteEmail().equals(employee1.geteEmail())){
					throw new EmployeeAlreadyExistsException("Email or PhoneNumber already in use..");
			}
		}
		String username = employee.getePhoneNumber()+"@pec";
		employee.seteUsername(username);
		Employee savedEmployee = employeeRepo.save(employee);
		return savedEmployee;
	}

	public Employee changePassword(String username,String oldPassword,String newPassword) {
		Employee employee = employeeRepo.findById(username).orElse(null);
		if(employee == null) {
			throw new InvalidCredentialsException("Invalid username...");
		}
		if(!employee.getePassword().equals(oldPassword)){
			throw new InvalidCredentialsException("Incorrect old password...");
		}
		employee.setePassword(newPassword);
		return employeeRepo.save(employee);
	}

	public boolean employeeLogin(String username, String password) {
		if(employeeRepo.existsById(username)) {
			Employee employee = employeeRepo.getOne(username);
			if(!employee.getePassword().equals(password)){
				throw new InvalidCredentialsException("Invalid credentials...");
			}
		}else {
			throw new InvalidCredentialsException("Invalid username...");
		}
		return true;
	}

	public Employee forgotPassword(Employee employee) {
		Employee employee1 = employeeRepo.findById(employee.geteUsername()).orElse(null);
		if(employee1 == null) {
			throw new InvalidCredentialsException("Invalid Credentials...");
		}
		if(!employee.getePhoneNumber().equals(employee1.getePhoneNumber())
				|| !employee.geteEmail().equalsIgnoreCase(employee1.geteEmail())){
			throw new InvalidCredentialsException("Invalid Credentials...");
		}
		employee1.setePassword(employee.getePassword());
		return employeeRepo.save(employee1);
	}

	public Employee getEmployee(String username) {
		System.out.println("Hello Inside getEmployee service with username"+username);
		Employee employee = employeeRepo.findById(username).orElse(null);
		System.out.println(employee);
		return employee;
	}
	
	

}
