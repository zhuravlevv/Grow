package com.epam;

import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    public List<Employee> getAll();

    public Optional<Employee> getById(Integer id);

    public Employee add(Employee employee);

    public Employee update(Employee employee, Integer id);

    public void delete(Integer id);
}
