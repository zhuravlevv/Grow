package com.epam.rest;

import com.epam.model.Department;
import com.epam.service_api.DepartmentService;
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
class DepartmentControllerTest {

    public static final String DEPARTMENTS_ENDPOINT = "/department";

    private MockMvc mockMvc;

    @Mock
    private DepartmentService departmentService;

    @InjectMocks
    private DepartmentController departmentController;

    @BeforeEach
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(departmentController)
                .build();
    }

    @Test
    public void shouldFindAllDepartments() throws Exception {
        Department department1 = new Department();
        department1.setName("department1");
        Mockito.when(departmentService.getAll()).thenReturn(Collections.singletonList(department1));
        mockMvc.perform(
                MockMvcRequestBuilders.get(DEPARTMENTS_ENDPOINT)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is(department1.getName())));
    }

//    @Test
//    public void shouldCreateDepartment() throws Exception {
//        Department department = new Department();
//        department.setName("department1");
//        Department department1 = departmentService.create(department);
//        assertNotNull(department);
//    }
//
//    @Test
//    public void shouldFindDepartmentById() throws Exception {
//
//        // given
//        Department department = new Department();
//        department.setName("department2");
//        Department department1 = departmentService.create(department);
//
//        assertNotNull(department1);
//
//        // when
//        Optional<Department> optionalDepartment = departmentService.findById(department1.getId());
//
//        // then
//        assertTrue(optionalDepartment.isPresent());
//        assertEquals(optionalDepartment.get().getId(), department1.getId());
//        assertEquals(optionalDepartment.get().getName(), department.getName());
//    }
//
//    @Test
//    public void shouldUpdateDepartment() throws Exception {
//
//        // given
//        Department department = new Department();
//        department.setName("department3");
//        Department department1 = departmentService.create(department);
//        assertNotNull(department1);
//
//        Department department2 = new Department();
//        department2.setName("department4");
//
//        // when
//        Department result = departmentService.update(department2, department1.getId());
//
//        // then
//        assertEquals(department1.getId(), result.getId());
//
//        Optional<Department> updatedDepartmentOptional = departmentService.findById(department1.getId());
//        assertTrue(updatedDepartmentOptional.isPresent());
//        assertEquals(updatedDepartmentOptional.get().getId(), department1.getId());
//        assertEquals(updatedDepartmentOptional.get().getName(),department2.getName());
//
//    }
//
//    @Test
//    public void shouldDeleteDepartment() throws Exception {
//        // given
//        Department department = new Department();
//        department.setName("department5");
//        Department department1 = departmentService.create(department);
//
//        List<Department> departments = departmentService.findAll();
//        assertNotNull(departments);
//
//        // when
//        departmentService.delete(department1.getId());
//
//        List<Department> currentDepartments = departmentService.findAll();
//        assertNotNull(currentDepartments);
//
//        assertEquals(departments.size() - 1, currentDepartments.size());
//    }
//
//    @Test
//    public void shouldReturnDepartmentNotFoundError() throws Exception {
//
//        MockHttpServletResponse response =
//                mockMvc.perform(MockMvcRequestBuilders.get(DEPARTMENTS_ENDPOINT + "/999999")
//                        .accept(MediaType.APPLICATION_JSON)
//                ).andExpect(status().isNotFound())
//                        .andReturn().getResponse();
//        assertNotNull(response);
//        ErrorResponse errorResponse = objectMapper.readValue(response.getContentAsString(), ErrorResponse.class);
//        assertNotNull(errorResponse);
//        assertEquals(errorResponse.getMessage(), DEPARTMENT_NOT_FOUND);
//    }

}
