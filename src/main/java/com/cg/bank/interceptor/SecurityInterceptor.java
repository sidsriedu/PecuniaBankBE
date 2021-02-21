package com.cg.bank.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cg.bank.dto.EmployeeDto;
import com.cg.bank.entities.Employee;
import com.cg.bank.service.IEmployeeService;
import com.cg.bank.util.TokenUtil;


@Component
public class SecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	TokenUtil util;
	@Autowired
	IEmployeeService employeeService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
		throws Exception	 {
		
		System.out.println("Inside preHandle");
		boolean isAuthorized=false;
		String uri=request.getRequestURI();
		System.out.println("uri is : - " + uri);
		String token=request.getHeader("Authorization");
		System.out.println("Token is : - " + token);
		if(uri.contains("/employees/login") || uri.contains("/employees/register") || uri.contains("/employees/forgotPassword") || uri.contains("/error")) {
			System.out.println("Inside true");
			isAuthorized=true;
			return isAuthorized;
		}
		if(token!=null) {
			EmployeeDto cred=util.decode(token);
			System.out.println(cred.geteUsername()+" "+cred.getePassword());
			Employee loggedInUser= employeeService.getEmployee(cred.geteUsername());
			System.out.println(loggedInUser+" "+cred.geteUsername());
			System.out.println("Request Intercepted...");
			if(loggedInUser==null) {
				throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
			}else {
				isAuthorized = true;
			}
		}
		
		if(!isAuthorized) {
			System.out.println("Hello Exception occurrred");
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
		}
		System.out.println(isAuthorized);
		return isAuthorized;
	}
	
	

}
