package com.epam;

import com.epam.config.TestConfigService;
import com.epam.model.Department;
import com.epam.model.Employee;
import com.epam.model.Language;
import com.epam.service_api.DepartmentService;
import com.epam.service_api.EmployeeService;
import com.epam.service_api.FakerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigService.class})
@Sql({"classpath:schema-h2.sql"})
public class FakerServiceImplTest {

    @Autowired
    private FakerService fakerService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void generateTest(){
        fakerService.generate(5);
        List<Department> departments = departmentService.getAll();
        List<Employee> employees = employeeService.getAll();
        assertTrue(departments.size()>=5);
        assertTrue(employees.size()>=15);
    }
}
