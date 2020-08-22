package com.epam;

import com.epam.dao.impl.EmployeeDaoImpl;
import com.epam.model.Employee;
import com.epam.service.EmployeeServiceImpl;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplMockTest {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeDaoImpl employeeDao;

    @Test
    public void getAll(){

        Mockito.when(employeeDao.getAll()).thenReturn(new ArrayList<>());
        List<Employee> employees = employeeService.getAll();

        Assert.assertNotNull(employees);
    }

    @Test
    public void getById() {
        Employee employee = new Employee("firstName", "lastName", 200.00, 1);
        employee.setId(1);
        Mockito.when(employeeDao.getById(1)).thenReturn(Optional.of(employee));

        Optional<Employee> returnedEmployee = employeeService.getById(1);

        try {
            Assert.assertEquals(employee, returnedEmployee.orElseThrow(Exception::new));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void add(){
        Employee employee = new Employee("firstName", "lastName", 200.00, 1);
        employee.setId(1);

        try {
            Mockito.when(employeeDao.add(Mockito.any(Employee.class))).thenReturn(employee);
            Employee returnedEmployee = employeeService.add(employee);
            Assert.assertEquals(employee, returnedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void update(){
        Employee employee = new Employee("firstName", "lastName", 200.00, 1);
        employee.setId(1);
        Employee newEmployee = new Employee("firstNameNew", "lastNameNew", 200.00, 1);

        try {
            Mockito.when(employeeDao.update(Mockito.any(Employee.class), Mockito.anyInt())).thenReturn(newEmployee);
            Employee returnedEmployee = employeeService.update(newEmployee, 1);
            Assert.assertEquals(newEmployee, returnedEmployee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
