package com.epam.impl;

import com.epam.Employee;
import com.epam.EmployeeDao;
import com.epam.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDao employeeDao;

    public EmployeeServiceImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public List<Employee> getAll() {
        LOGGER.trace("getAll()");
        return employeeDao.getAll();
    }

    @Override
    public Optional<Employee> getById(Integer id) {
        LOGGER.trace("getById(id: {})", id);
        return employeeDao.getById(id);
    }

    @Override
    public Employee add(Employee employee) throws Exception {
        LOGGER.trace("add(employee: {})", employee);
        return employeeDao.add(employee);
    }

    @Override
    public Employee update(Employee newEmployee, Integer id) throws Exception {
        LOGGER.trace("update(newEmployee: {}, id: {})", newEmployee, id);
        return employeeDao.update(newEmployee, id);
    }

    @Override
    public void delete(Integer id) {
        LOGGER.trace("delete(id: {})", id);
        employeeDao.delete(id);
    }
}
