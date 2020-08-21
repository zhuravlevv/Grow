package com.epam.service_rest;

import com.epam.model.Department;
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
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class DepartmentServiceRestTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceRestTest.class);

    public static final String DEPARTMENTS_URL = "http://localhost:8088/department";

    @Autowired
    RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    private ObjectMapper mapper = new ObjectMapper();

    DepartmentServiceRest departmentService;

    @BeforeEach
    public void before() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
        departmentService = new DepartmentServiceRest(DEPARTMENTS_URL, restTemplate);
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {

        LOGGER.debug("shouldFindAllDepartments()");
        // given
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(Arrays.asList(create(0), create(1))))
                );

        // when
        List<Department> departments = departmentService.getAll();

        // then
        mockServer.verify();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldCreateDepartment() throws Exception {

        LOGGER.debug("shouldCreateDepartment()");
        // given
        Department department = new Department();
        department.setName("departmentNameShouldCreateDepartment");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        Department department1 = departmentService.add(department);

        // then
        mockServer.verify();
        assertNotNull(department1);
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {

        // given
        Integer id = 1;
        Department department = new Department();
        department.setId(id);
        department.setName("departmentNameShouldFindDepartmentById");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );

        // when
        Optional<Department> optionalDepartment = departmentService.getById(id);

        // then
        mockServer.verify();
        assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getId(), id);
        assertEquals(optionalDepartment.get().getName(), department.getName());
    }

    @Test
    public void shouldUpdateDepartment() throws Exception {

        // given
        Integer id = 1;
        Department department = new Department();
        department.setId(id);
        department.setName("departmentNameShouldUpdateDepartment");

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL)))
                .andExpect(method(HttpMethod.PUT))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );

        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString(department))
                );

        // when
        Department result = departmentService.update(department, 1);
        Optional<Department> updatedDepartmentOptional = departmentService.getById(id);

        // then
        mockServer.verify();

        assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getId(), id);
        assertEquals(updatedDepartmentOptional.get().getName(), department.getName());
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        // given
        Integer id = 1;
        mockServer.expect(ExpectedCount.once(), requestTo(new URI(DEPARTMENTS_URL + "/" + id)))
                .andExpect(method(HttpMethod.DELETE))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapper.writeValueAsString("1"))
                );
        // when
        departmentService.delete(id);

        // then
        mockServer.verify();
    }

    private Department create(int index) {
        Department department = new Department();
        department.setId(index);
        department.setName("d" + index);
        return department;
    }
}
