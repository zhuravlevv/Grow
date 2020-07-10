package com.epam.rest;

import com.epam.model.Employee;
import com.epam.rest.exception.CustomExceptionHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class EmployeeControllerTest {

    public static final String EMPLOYEES_ENDPOINT = "/employee";

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcEmployeeService employeeService =  new MockMvcEmployeeService();


    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {

        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldFindEmployeesByDepartmentId() throws Exception {

        Employee employee = new Employee();
        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);
        Employee employee1 = employeeService.create(employee);

        List<Employee> employees = employeeService.findByDepartmentId(1);
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
        for (Employee e: employees) {
            assertEquals(Integer.valueOf(1), e.getDepartmentId());
        }
    }

    @Test
    public void shouldFindEmployeeById() throws Exception {

        // given
        Employee employee = new Employee();
        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);
        Employee employee1 = employeeService.create(employee);

        // when
        Optional<Employee> optionalEmployee = employeeService.findById(employee1.getId());

        // then
        Assertions.assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getId(), employee1.getId());
        assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
        assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
        assertEquals(optionalEmployee.get().getSalary(), employee.getSalary());
    }

    @Test
    public void shouldCreateEmployee() throws Exception {
        Employee employee = new Employee();
        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);

        Employee employee1 = employeeService.create(employee);
        assertNotNull(employee1);
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {

        // given
        Employee employee = new Employee();
        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);

        Employee employee1 = employeeService.create(employee);
        assertNotNull(employee1.getId());

        Employee employee2 = new Employee();
        employee2.setFirstName("first_name2");
        employee.setLastName("last_name");
        employee.setSalary(200d);
        employee.setDepartmentId(1);

        // when
        Employee result = employeeService.update(employee2, employee1.getId());

        assertEquals(result.getId(), employee1.getId());
        assertEquals(result.getFirstName(), employee2.getFirstName());

    }

    @Test
    public void shouldDeleteEmployee() throws Exception {
        // given
        Employee employee = new Employee();
        employee.setFirstName("first_name");
        employee.setLastName("last_name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);
        Employee result = employeeService.create(employee);

        List<Employee> employees = employeeService.findAll();
        assertNotNull(employees);

        // when
        employeeService.delete(result.getId());

        List<Employee> currentEmployees = employeeService.findAll();
        assertNotNull(currentEmployees);

        assertEquals(employees.size() - 1, currentEmployees.size());
    }

    ///////////////////////////////////////////////////////////////////

    private class MockMvcEmployeeService {

        public List<Employee> findAll() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Employee>>() {});
        }

        private Optional<Employee> findById(Integer employeeId) throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT + "/" + employeeId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Employee.class));
        }

        private Employee create(Employee employee) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(post(EMPLOYEES_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(employee))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Employee.class);
        }

        private Employee update(Employee employee, Integer id) throws Exception {
            MockHttpServletResponse response =
                    mockMvc.perform(put(EMPLOYEES_ENDPOINT + "/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(employee))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Employee.class);
        }

        private void delete(Integer employeeId) throws Exception {

            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(EMPLOYEES_ENDPOINT + "/" +
                            employeeId)
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNoContent())
                    .andReturn().getResponse();
        }

        public List<Employee> findByDepartmentId(int id) throws Exception {

            MockHttpServletResponse response = mockMvc.perform(get(EMPLOYEES_ENDPOINT + "_dep")
                    .param("id", String.valueOf(id))
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Employee>>() {});
        }
    }

}
