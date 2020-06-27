package com.epam;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public List<Employee> getAll();

    public Optional<Employee> getById(Integer id);

    public Employee add(Employee employee) throws Exception;

    public Employee update(Employee newEmployee, Integer id) throws Exception;

    public void delete(Integer id);
}