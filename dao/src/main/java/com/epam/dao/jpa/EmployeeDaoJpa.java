package com.epam.dao.jpa;

import com.epam.model.Employee;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Profile("jpa")
@Repository("employeeDao")
public interface EmployeeDaoJpa extends CrudRepository<Employee, Integer> {

    public List<Employee> getAll();
    public Employee getById(Integer id);
    public Employee create(Employee employee);
    public Employee update(Integer id, Employee employee);
    public void deleteById(Integer id);
}
