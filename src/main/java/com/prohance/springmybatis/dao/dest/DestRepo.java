package com.prohance.springmybatis.dao.dest;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.springmybatis.model.Employee;

@Repository
public interface DestRepo {
	
	@Select("select * from employee")
	List<Employee> getAllEmployees();
	
	@Insert("INSERT INTO employee(employee_id, name, gender, salary, city, country)"
	  + "VALUES(#{employeeId}, #{name}, #{gender}, #{salary}, #{city}, #{country})")
	void save(Employee employee);
	
	@Delete(" DELETE FROM employee WHERE employee_id = #{employeeId}")
	void delete(String empId);
	
	@Update("UPDATE employee SET name = #{name}, gender = #{gender}, salary =#{salary}, city =#{city}, country =#{country}\r\n" + 
			"  WHERE employee_id = #{employeeId}")
	void update(Employee employee);
	
	

}
