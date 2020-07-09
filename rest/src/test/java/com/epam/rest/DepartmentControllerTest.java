package com.epam.rest;

import com.epam.model.Department;
import com.epam.rest.exception.CustomExceptionHandler;
import com.epam.rest.exception.ErrorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.epam.rest.exception.CustomExceptionHandler.DEPARTMENT_NOT_FOUND;
import static com.epam.rest.exception.CustomExceptionHandler.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
class DepartmentControllerTest {

    public static final String DEPARTMENTS_ENDPOINT = "/department";

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private CustomExceptionHandler customExceptionHandler;

    ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    MockMvcDepartmentService departmentService = new MockMvcDepartmentService();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .setControllerAdvice(customExceptionHandler)
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {

        List<Department> departments = departmentService.findAll();
        assertNotNull(departments);
        assertTrue(departments.size() > 0);
    }

    @Test
    public void shouldCreateDepartment() throws Exception {
        Department department = new Department();
        department.setName("department1");
        Department department1 = departmentService.create(department);
        assertNotNull(department);
    }

    @Test
    public void shouldFindDepartmentById() throws Exception {

        // given
        Department department = new Department();
        department.setName("department2");
        Department department1 = departmentService.create(department);

        assertNotNull(department1);

        // when
        Optional<Department> optionalDepartment = departmentService.findById(department1.getId());

        // then
        assertTrue(optionalDepartment.isPresent());
        assertEquals(optionalDepartment.get().getId(), department1.getId());
        assertEquals(optionalDepartment.get().getName(), department.getName());
    }

    @Test
    public void shouldUpdateDepartment() throws Exception {

        // given
        Department department = new Department();
        department.setName("department3");
        Department department1 = departmentService.create(department);
        assertNotNull(department1);

//        Optional<Department> departmentOptional = departmentService.findById(department1.getId());
//        assertTrue(departmentOptional.isPresent());
        Department department2 = new Department();
        department2.setName("department4");
//        departmentOptional.get().setName("department");

        // when
        Department result = departmentService.update(department2, department1.getId());

        // then
        assertEquals(department1.getId(), result.getId());

        Optional<Department> updatedDepartmentOptional = departmentService.findById(department1.getId());
        assertTrue(updatedDepartmentOptional.isPresent());
        assertEquals(updatedDepartmentOptional.get().getId(), department1.getId());
        assertEquals(updatedDepartmentOptional.get().getName(),department2.getName());

    }

    @Test
    public void shouldDeleteDepartment() throws Exception {
        // given
        Department department = new Department();
        department.setName("department5");
        Department department1 = departmentService.create(department);

        List<Department> departments = departmentService.findAll();
        assertNotNull(departments);

        // when
        departmentService.delete(department1.getId());

        List<Department> currentDepartments = departmentService.findAll();
        assertNotNull(currentDepartments);

        assertEquals(departments.size() - 1, currentDepartments.size());
    }

    @Test
    public void shouldReturnDepartmentNotFoundError() throws Exception {

        MockHttpServletResponse response =
                mockMvc.perform(MockMvcRequestBuilders.get(DEPARTMENTS_ENDPOINT + "/999999")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isNotFound())
                        .andReturn().getResponse();
        assertNotNull(response);
        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
        assertNotNull(errorResponse);
        assertEquals(errorResponse.getMessage(), DEPARTMENT_NOT_FOUND);
    }

    class MockMvcDepartmentService {

        public List<Department> findAll() throws Exception {
            MockHttpServletResponse response = mockMvc.perform(get(DEPARTMENTS_ENDPOINT)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            assertNotNull(response);

            return objectMapper.readValue(response.getContentAsString(), new TypeReference<List<Department>>() {});
        }

        public Optional<Department> findById(Integer departmentId) throws Exception {

            MockHttpServletResponse response = mockMvc.perform(get(DEPARTMENTS_ENDPOINT + "/" + departmentId)
                    .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk())
                    .andReturn().getResponse();
            return Optional.of(objectMapper.readValue(response.getContentAsString(), Department.class));
        }

        public Department create(Department department) throws Exception {

            String json = objectMapper.writeValueAsString(department);
            MockHttpServletResponse response =
                    mockMvc.perform(post(DEPARTMENTS_ENDPOINT)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isCreated())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Department.class);
        }

        private Department update(Department department, Integer id) throws Exception {

            MockHttpServletResponse response =
                    mockMvc.perform(put(DEPARTMENTS_ENDPOINT + "/" + id)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(department))
                            .accept(MediaType.APPLICATION_JSON)
                    ).andExpect(status().isOk())
                            .andReturn().getResponse();
            return objectMapper.readValue(response.getContentAsString(), Department.class);
        }

        private void delete(Integer departmentId) throws Exception {

            MockHttpServletResponse response = mockMvc.perform(
                    MockMvcRequestBuilders.delete(DEPARTMENTS_ENDPOINT + "/" +
                            departmentId)
                            .accept(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNoContent())
                    .andReturn().getResponse();
        }
    }
}
