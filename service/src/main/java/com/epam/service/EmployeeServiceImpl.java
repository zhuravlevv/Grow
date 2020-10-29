package com.epam.service;

import com.epam.dao.EmployeeDao;
import com.epam.model.Employee;
import com.epam.service_api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
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
    @Transactional
    public Employee add(Employee employee) throws Exception {
        LOGGER.trace("add(employee: {})", employee);
        int id = employeeDao.add(employee);
        return getById(id).orElseThrow(Exception::new);
    }

    @Override
    @Transactional
    public Employee update(Employee newEmployee) throws Exception {
        LOGGER.trace("update(newEmployee: {})", newEmployee);
        employeeDao.update(newEmployee);
        return getById(newEmployee.getId()).orElseThrow(Exception::new);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        LOGGER.trace("delete(id: {})", id);
        employeeDao.delete(id);
    }

    @Override
    public List<Employee> getByDepartmentId(Integer id) {
        LOGGER.trace("getByDepartmentId({})", id);
        return employeeDao.getByDepartmentId(id);
    }
}
