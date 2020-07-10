package com.epam.dao;


import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    public List<Employee> getAll();

    public Optional<Employee> getById(Integer id);

    public Employee add(Employee employee) throws Exception;

    public Employee update(Employee employee, Integer id) throws Exception;

    public void delete(Integer id);

    public List<Employee> getByDepartmentId(Integer id);
}
