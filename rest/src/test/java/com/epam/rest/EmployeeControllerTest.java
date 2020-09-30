package com.epam.rest;

import com.epam.model.Employee;
import com.epam.service_api.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    public static final String EMPLOYEES_ENDPOINT = "/employee";

    private MockMvc mockMvc;

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @BeforeEach
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .build();
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("firstName1");
        Mockito.when(employeeService.getAll()).thenReturn(Collections.singletonList(employee));
        mockMvc.perform(
                MockMvcRequestBuilders.get(EMPLOYEES_ENDPOINT)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(employee.getFirstName())));
    }

//    @Test
//    public void shouldFindEmployeesByDepartmentId() throws Exception {
//
//        Employee employee = new Employee();
//        employee.setFirstName("first_name");
//        employee.setLastName("last_name");
//        employee.setSalary(100d);
//        employee.setDepartmentId(1);
//        Employee employee1 = employeeService.create(employee);
//
//        List<Employee> employees = employeeService.findByDepartmentId(1);
//        assertNotNull(employees);
//        assertTrue(employees.size() > 0);
//        for (Employee e: employees) {
//            assertEquals(Integer.valueOf(1), e.getDepartmentId());
//        }
//    }
//
//    @Test
//    public void shouldFindEmployeeById() throws Exception {
//
//        // given
//        Employee employee = new Employee();
//        employee.setFirstName("first_name");
//        employee.setLastName("last_name");
//        employee.setSalary(100d);
//        employee.setDepartmentId(1);
//        Employee employee1 = employeeService.create(employee);
//
//        // when
//        Optional<Employee> optionalEmployee = employeeService.findById(employee1.getId());
//
//        // then
//        Assertions.assertTrue(optionalEmployee.isPresent());
//        assertEquals(optionalEmployee.get().getId(), employee1.getId());
//        assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
//        assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
//        assertEquals(optionalEmployee.get().getSalary(), employee.getSalary());
//    }
//
//    @Test
//    public void shouldCreateEmployee() throws Exception {
//        Employee employee = new Employee();
//        employee.setFirstName("first_name");
//        employee.setLastName("last_name");
//        employee.setSalary(100d);
//        employee.setDepartmentId(1);
//
//        Employee employee1 = employeeService.create(employee);
//        assertNotNull(employee1);
//    }
//
//    @Test
//    public void shouldUpdateEmployee() throws Exception {
//
//        // given
//        Employee employee = new Employee();
//        employee.setFirstName("first_name");
//        employee.setLastName("last_name");
//        employee.setSalary(100d);
//        employee.setDepartmentId(1);
//
//        Employee employee1 = employeeService.create(employee);
//        assertNotNull(employee1.getId());
//
//        Employee employee2 = new Employee();
//        employee2.setFirstName("first_name2");
//        employee.setLastName("last_name");
//        employee.setSalary(200d);
//        employee.setDepartmentId(1);
//
//        // when
//        Employee result = employeeService.update(employee2, employee1.getId());
//
//        assertEquals(result.getId(), employee1.getId());
//        assertEquals(result.getFirstName(), employee2.getFirstName());
//
//    }
//
//    @Test
//    public void shouldDeleteEmployee() throws Exception {
//        // given
//        Employee employee = new Employee();
//        employee.setFirstName("first_name");
//        employee.setLastName("last_name");
//        employee.setSalary(100d);
//        employee.setDepartmentId(1);
//        Employee result = employeeService.create(employee);
//
//        List<Employee> employees = employeeService.findAll();
//        assertNotNull(employees);
//
//        // when
//        employeeService.delete(result.getId());
//
//        List<Employee> currentEmployees = employeeService.findAll();
//        assertNotNull(currentEmployees);
//
//        assertEquals(employees.size() - 1, currentEmployees.size());
//    }

}
