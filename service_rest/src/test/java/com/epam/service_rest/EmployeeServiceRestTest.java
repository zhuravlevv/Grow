package com.epam.service_rest;


import com.epam.model.Employee;
import com.epam.service_rest.config.TestConfigRestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfigRestService.class})
public class EmployeeServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRestTest.class);

    public static final String URL = "http://localhost:8088/employee";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    EmployeeServiceRest employeeService;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        employeeService = new EmployeeServiceRest(URL, restTemplate);
    }

    @Test
    public void shouldFindAllEmployees() throws Exception {

        LOGGER.debug("shouldFindAllEmployees()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Employee> employees = employeeService.getAll();

        // then
        mockServer.verify();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    void shouldFindByDepartmentId() throws Exception {

        LOGGER.debug("shouldFindByDepartmentId()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "?departmentId=1")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Employee> employees = employeeService.getByDepartmentId(1);

        // then
        mockServer.verify();
        assertNotNull(employees);
        assertTrue(employees.size() > 0);
    }

    @Test
    public void shouldCreateEmployee() throws Exception {

        LOGGER.debug("shouldCreateEmployee()");
        // given
        Employee employee = new Employee();
        employee.setFirstName("firstNameEmployeeShouldCreateEmployee");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Employee employee1 = employeeService.add(employee);

        // then
        mockServer.verify();
        assertNotNull(employee1);
    }

    @Test
    public void shouldFindEmployeeById() throws Exception {

        // given
        Integer id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("firstNameEmployeeShouldFindEmployeeById");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        Optional<Employee> optionalEmployee = employeeService.getById(id);

        // then
        mockServer.verify();
        assertTrue(optionalEmployee.isPresent());
        assertEquals(optionalEmployee.get().getId(), id);
        assertEquals(optionalEmployee.get().getFirstName(), employee.getFirstName());
        assertEquals(optionalEmployee.get().getLastName(), employee.getLastName());
    }

    @Test
    public void shouldUpdateEmployee() throws Exception {

        // given
        Integer id = 1;
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName("firstNameEmployeeShouldUpdateEmployee");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(employee))
                );

        // when
        Employee result = employeeService.update(employee);
        Optional<Employee> updatedEmployeeOptional = employeeService.getById(id);

        // then
        mockServer.verify();

        assertTrue(updatedEmployeeOptional.isPresent());
        assertEquals(updatedEmployeeOptional.get().getId(), id);
        assertEquals(updatedEmployeeOptional.get().getFirstName(), employee.getFirstName());
        assertEquals(updatedEmployeeOptional.get().getLastName(), employee.getLastName());
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        employeeService.delete(id);

        // then
        mockServer.verify();
    }

    private Employee create(int index) {
        Employee employee = new Employee();
        employee.setId(index);
        employee.setFirstName("f" + index);
        employee.setLastName("l" + index);
        return employee;
    }
}
