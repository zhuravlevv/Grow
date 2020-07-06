package com.epam.web_app;

import com.epam.model.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class DepartmentControllerIT {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void shouldReturnDepartmentsPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/departments")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("departments"))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("SEO")),
                                hasProperty("averageSalary", is(500.00))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("name", is("Development")),
                                hasProperty("averageSalary", is(1900.00))
                        )
                )))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(3)),
                                hasProperty("name", is("Production")),
                                hasProperty("averageSalary", is(0.00))
                        )
                )));
    }

    @Test
    public void shouldOpenEditDepartmentPageById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("department", hasProperty("id", is(1))))
                .andExpect(model().attribute("department", hasProperty("name", is("SEO"))));
    }

    @Test
    public void shouldReturnToDepartmentsPageIfDepartmentNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("departments"));
    }

    @Test
    public void shouldOpenNewDepartmentPage() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders.get("/department")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("department"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("department", isA(Department.class)));
    }

    @Test
    public void shouldAddNewDepartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("departmentName", "test")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void shouldDeleteDepartment() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void shouldUpdateDepartmentAfterEdit() throws Exception {

        Department department = new Department();
        department.setName("test");
        department.setId(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("name", "test")
                        .sessionAttr("department", department)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/departments"))
                .andExpect(redirectedUrl("/departments"));
    }

    @Test
    public void shouldRejectUpdateDepartmentOnLargeDepartmentName() throws Exception {

        Department department = new Department();
        department.setId(1);
        department.setName("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", "1")
                        .param("name", department.getName())
                        .sessionAttr("department", department)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(view().name("department"));
    }

    @Test
    public void shouldRejectAddDepartmentOnLargeDepartmentName() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post("/department")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa")
        ).andExpect(status().isOk())
                .andExpect(view().name("department"));
    }
}
