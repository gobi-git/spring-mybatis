package com.prohance.springmybatis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prohance.springmybatis.dao.dest.DestRepo;
import com.prohance.springmybatis.dao.source.SourceRepo;
import com.springmybatis.model.Employee;

@Service
public class EmployeeDataProcessorService {

	@Autowired
	private SourceRepo sourceRepo;

	@Autowired
	private DestRepo destRepo;

	public Map<String, List<Employee>> process() {

		HashMap<String, List<Employee>> dataLists = new HashMap<String, List<Employee>>();
		List<Employee> allEmployees = sourceRepo.getAllEmployees();
		List<Employee> allEmployeesFromDest = destRepo.getAllEmployees();

		System.out.println("==========================================================");

		List<Employee> insertList = allEmployees.stream().filter(one -> !allEmployeesFromDest.contains(one))
				.collect(Collectors.toList());
		dataLists.put("insertList", insertList);

		List<Employee> deleteList = allEmployeesFromDest.stream().filter(one -> !allEmployees.contains(one))
				.collect(Collectors.toList());
		dataLists.put("deleteList", deleteList);

		List<Employee> updateList = allEmployees.stream().filter(record -> allEmployeesFromDest.contains(record))
				.filter(two -> !allEmployeesFromDest.stream()
						.anyMatch(one -> one.getName().equals(two.getName()) && one.getSalary().equals(two.getSalary())
								&& one.getCity().equals(two.getCity()) && one.getCountry().equals(two.getCountry())
								&& one.getGender().equals(two.getGender()))) //
				.collect(Collectors.toList());
		dataLists.put("updateList", updateList);

		dataLists.entrySet().forEach(entry -> {
			System.out.println(entry.getKey());
			entry.getValue().stream().forEach(System.out::println);
		});

		System.out.println("==========================================================");

		if (!dataLists.get("insertList").isEmpty()) { // ! logical not operator?, is empty, getting insertlist, checking
														// if is empty it true,
			dataLists.get("insertList").forEach(emp -> {
				destRepo.save(emp);
			});
			System.out.println("insert list employees are saved into destination database....");
		}

		if (!dataLists.get("deleteList").isEmpty()) { // list we have .empty method, return true, to check the list is
														// empty or not.
			dataLists.get("deleteList").forEach(emp -> {
				destRepo.delete(emp.getEmployeeId()); //
			});
			System.out.println("delete list employees are deleted from destination database...");
		}

		if (!dataLists.get("updateList").isEmpty()) {
			dataLists.get("updateList").forEach(emp -> {
				destRepo.update(emp);
			});
			System.out.println("update list employees are updated in destination database....");
		}

		System.out.println("==========================================================");
		return dataLists;

	}

}
