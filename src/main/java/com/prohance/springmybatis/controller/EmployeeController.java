package com.prohance.springmybatis.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.prohance.springmybatis.dao.dest.DestRepo;
import com.prohance.springmybatis.dao.source.SourceRepo;
import com.prohance.springmybatis.service.EmployeeDataProcessorService;
import com.springmybatis.model.Employee;

@RestController
public class EmployeeController {

	@Autowired
	private SourceRepo sourceRepo;

	@Autowired
	private DestRepo destRepo;

	@Autowired
	private EmployeeDataProcessorService employeeService;

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

	@GetMapping("/comparison")
	public ResponseEntity<Map<String, Object>> compare() {
		HashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
		try {
			Map<String, List<Employee>> empListsMap = employeeService.process();
			resMap.put("success", true);
			resMap.put("data", empListsMap);
			return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.OK);
		} catch (Exception e) {
			resMap.put("success", false);
			return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@PostMapping("/createEmployee")
	public ResponseEntity<Map<String, Object>> addEmployee(@RequestBody() Employee employee) {
		HashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
		try {
			sourceRepo.save(employee);
			resMap.put("success", true);
			return new ResponseEntity<Map<String, Object>>(resMap,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put("success", false);
			return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/updateEmployee")
	public ResponseEntity<Map<String, Object>> updateEmployee(@RequestBody() Employee employee) {
		HashMap<String, Object> resMap = new LinkedHashMap<String, Object>();
		try {
			sourceRepo.update(employee);
			resMap.put("success", true);
			return new ResponseEntity<Map<String, Object>>(resMap,HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			resMap.put("success", false);
			return new ResponseEntity<Map<String, Object>>(resMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
