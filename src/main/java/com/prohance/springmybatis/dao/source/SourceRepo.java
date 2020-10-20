package com.prohance.springmybatis.dao.source;

import java.util.List;

import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import com.springmybatis.model.Employee;

@Repository
public interface SourceRepo {
	@Select("select * from employee")
	List<Employee> getAllEmployees();

}
