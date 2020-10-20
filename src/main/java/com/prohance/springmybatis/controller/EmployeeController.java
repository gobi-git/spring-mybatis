package com.prohance.springmybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prohance.springmybatis.dao.dest.DestRepo;
import com.prohance.springmybatis.dao.source.SourceRepo;
import com.springmybatis.model.Employee;

@RestController
public class EmployeeController {
	
	@Autowired
	private SourceRepo sourceRepo;
	
	@Autowired
	private DestRepo destRepo;
	
	@GetMapping("/")
	public String hello() {
		return "Welcome to Employee Service !!";
	}
	
	@GetMapping("/allEmployeesFromSrc")
	public List<Employee> getAllEmployeeFromSrc() {
		return sourceRepo.getAllEmployees();
		
	}
	
	@GetMapping("/allEmployeesFromDest")
	public List<Employee> getAllEmployeeFromDest() {
		return destRepo.getAllEmployees();
		
	}

}
