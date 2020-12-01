package com.epam.dao;


import com.epam.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    public List<Employee> findAll();

    public Optional<Employee> findById(Integer id);

    public Employee save(Employee employee);

    public int update(Employee employee);

    public void deleteById(Integer id);

    public List<Employee> findByDepartmentId(Integer id);
}
