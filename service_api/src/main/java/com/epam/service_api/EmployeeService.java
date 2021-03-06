package com.epam.service_api;

import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<Employee> getAll();

    public Optional<Employee> getById(Integer id);

    public Employee add(Employee employee);

    public Employee update(Employee newEmployee) throws Exception;

    public void delete(Integer id);

    public List<Employee> getByDepartmentId(Integer id);
}
