package com.epam.dao;


import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    public List<Employee> getAll();

    public Optional<Employee> getById(Integer id);

    public Employee save(Employee employee);

    public int update(Employee employee);

    public void delete(Integer id);

    public List<Employee> getByDepartmentId(Integer id);
}
