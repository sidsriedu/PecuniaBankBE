package com.cg.bank.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bank.dto.EmployeeDto;
import com.cg.bank.dto.MessageDto;
import com.cg.bank.dto.TokenDto;
import com.cg.bank.entities.Employee;
import com.cg.bank.exceptions.EmployeeAlreadyExistsException;
import com.cg.bank.exceptions.InvalidCredentialsException;
import com.cg.bank.service.IEmployeeService;
import com.cg.bank.util.TokenUtil;

@RestController
@CrossOrigin
@RequestMapping("/employees")
public class EmployeeController {
	
	@Autowired
	IEmployeeService employeeService;
	@Autowired
	TokenUtil util;
	
	private static final Logger log = LoggerFactory.getLogger(EmployeeController.class); 
	
	@PostMapping("/register")
	ResponseEntity<EmployeeDto> addEmployee(@RequestBody EmployeeDto employeeDto){
		System.out.println("Hello");
		Employee employee = new Employee();
		employee.seteName(employeeDto.geteName());
		employee.setePhoneNumber(employeeDto.getePhoneNumber());
		employee.seteEmail(employeeDto.geteEmail());
		employee.setePassword(employeeDto.getePassword());
		Employee addedEmployee = employeeService.addEmployee(employee);
		EmployeeDto addedEmployeeDto = new EmployeeDto();
		addedEmployeeDto.seteUsername(addedEmployee.geteUsername());
		addedEmployeeDto.seteName(addedEmployee.geteName());
		addedEmployeeDto.setePhoneNumber(addedEmployee.getePhoneNumber());
		addedEmployeeDto.seteEmail(addedEmployee.geteEmail());
		addedEmployeeDto.setePassword(addedEmployee.getePassword());
		ResponseEntity<EmployeeDto> response = new ResponseEntity<EmployeeDto>(addedEmployeeDto,HttpStatus.OK);
		return response;
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> employeeLogin(@RequestBody EmployeeDto employeeDto){
		System.out.println(employeeDto.geteUsername()+" "+ employeeDto.getePassword());
		employeeService.employeeLogin(employeeDto.geteUsername(), employeeDto.getePassword());
		System.out.println(employeeDto.geteUsername()+" "+employeeDto.getePassword());
		ResponseEntity<TokenDto> response = new ResponseEntity<TokenDto>(new TokenDto(util.generateToken(employeeDto)),HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/changePassword")
	ResponseEntity<MessageDto> changePassword(@RequestBody EmployeeDto employeeDto){
		System.out.println(employeeDto.geteUsername());
		employeeService.changePassword(employeeDto.geteUsername(), employeeDto.geteOldPassword(), employeeDto.getePassword());
		ResponseEntity<MessageDto> response = new ResponseEntity<MessageDto>(new MessageDto("success"),HttpStatus.OK);
		return response;
	}
	
	@PutMapping("/forgotPassword")
	ResponseEntity<MessageDto> forgotPassword(@RequestBody EmployeeDto employeeDto){
		Employee employee = new Employee();
		employee.seteUsername(employeeDto.geteUsername());
		employee.setePhoneNumber(employeeDto.getePhoneNumber());
		employee.seteEmail(employeeDto.geteEmail());
		employee.setePassword(employeeDto.getePassword());
		employeeService.forgotPassword(employee);
		ResponseEntity<MessageDto> response = new ResponseEntity<MessageDto>(new MessageDto("success"),HttpStatus.OK);
		return response;
	}
	
	@GetMapping("/authentication")
	ResponseEntity<MessageDto> authentication(){
		ResponseEntity<MessageDto> response = new ResponseEntity<MessageDto>(new MessageDto("success"),HttpStatus.OK);
		return response;
	}
	
	@ExceptionHandler(EmployeeAlreadyExistsException.class)
	public ResponseEntity<String> handleExceptionEmployeeAlreadyExists(EmployeeAlreadyExistsException exception){
		 log.error("Employee Already Exists Exception",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.CONFLICT);
	     return response;
	}
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<String> handleExceptionInvalidCredentials(InvalidCredentialsException exception){
		 log.error("Invalid Credentials...",exception);
		 String msg = exception.getMessage();
	     ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.NOT_ACCEPTABLE);
	     return response;
	}
	
	@ExceptionHandler(Throwable.class)
    public ResponseEntity<String> handleAll(Throwable ex) {
        log.error("exception caught", ex);
        System.out.println("Throwable");
        String msg = ex.getMessage();
        ResponseEntity<String> response = new ResponseEntity<String>(msg, HttpStatus.INTERNAL_SERVER_ERROR);
        return response;
    }
}
