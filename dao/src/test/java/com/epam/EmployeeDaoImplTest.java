package com.epam;

import com.epam.dao.EmployeeDao;
import com.epam.model.Employee;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath*:test-dao.xml", "classpath*:test-db.xml", "classpath:dao.xml"})
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void getAll(){
        List<Employee> employees = employeeDao.getAll();
        Assert.assertNotNull(employees);
    }

    @Test
    public void getById() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        Assert.assertEquals(addedEmployee, searchedEmployee);
    }

    @Test
    public void add() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        Assert.assertEquals(addedEmployee, searchedEmployee);
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee employee1 = new Employee("first_name1", "last_name", 100.0, 1);

        employeeDao.update(employee1, addedEmployee.getId());

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        Assert.assertEquals(employee1.getFirstName(), searchedEmployee.getFirstName());

    }

    @Test
    public void delete() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        employeeDao.delete(addedEmployee.getId());

        Assert.assertFalse(employeeDao.getById(addedEmployee.getId()).isPresent());
    }
}
