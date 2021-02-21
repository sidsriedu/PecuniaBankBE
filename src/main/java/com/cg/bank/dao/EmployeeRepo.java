package com.cg.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cg.bank.entities.Employee;
@Repository
public interface EmployeeRepo extends JpaRepository<Employee, String> {

}
