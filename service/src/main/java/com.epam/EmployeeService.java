package com.epam;

import com.epam.Employee;

import java.util.List;

public interface EmployeeService {

    public List<Employee> getAll();

    public Employee getById(Integer id);

    public Employee add(Employee employee);

    public Employee update(Employee newEmployee, Integer id);

    public void delete(Integer id);
}
