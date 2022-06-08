package com.demo.service;

import com.demo.model.Condition;
import com.demo.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    Employee getEmployeeById(Integer ID);
    void addEmployee(Employee employee);
    boolean deleteEmployee(Integer ID);
    void updateEmployee(Employee employee);

    List<Employee> fineEmployees(List<Condition> conditions);

    List<Employee> findEmployees(Condition condition);

}
