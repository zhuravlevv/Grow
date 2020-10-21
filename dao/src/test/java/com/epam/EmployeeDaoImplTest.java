package com.epam;

import com.epam.config.TestConfigDao;
import com.epam.dao.EmployeeDao;
import com.epam.model.Employee;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes={TestConfigDao.class})
@Sql({"classpath:schema-h2.sql", "classpath:data-h2.sql"})
public class EmployeeDaoImplTest {

    @Autowired
    private EmployeeDao employeeDao;

    @Test
    public void getAll(){
        List<Employee> employees = employeeDao.getAll();
        assertNotNull(employees);
    }

    @Test
    public void getById() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        assertEquals(addedEmployee, searchedEmployee);
    }

    @Test
    public void add() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        assertEquals(addedEmployee, searchedEmployee);
    }

    @Test
    public void update() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        Employee employee1 = new Employee("first_name1", "last_name", 100.0, 1);

        employeeDao.update(employee1, addedEmployee.getId());

        Employee searchedEmployee = employeeDao.getById(addedEmployee.getId()).orElseThrow(Exception::new);

        assertEquals(employee1.getFirstName(), searchedEmployee.getFirstName());

    }

    @Test
    public void delete() throws Exception {
        Employee employee = new Employee("first_name", "last_name", 100.0, 1);
        Employee addedEmployee = employeeDao.add(employee);

        employeeDao.delete(addedEmployee.getId());

        assertFalse(employeeDao.getById(addedEmployee.getId()).isPresent());
    }

    @Test
    public void getByDepartmentId() {

        List<Employee> employees = employeeDao.getByDepartmentId(1);
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }
}
