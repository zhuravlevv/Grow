package com.epam.web_app;

import com.epam.model.Employee;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:app-context-test.xml"})
public class EmployeeControllerIT {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void shouldReturnEmployeesPage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employees")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employees"))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("firstName", is("vlad")),
                                hasProperty("lastName", is("zhuravlev")),
                                hasProperty("salary", is(500d))
                        )
                )))
                .andExpect(model().attribute("employees", hasItem(
                        allOf(
                                hasProperty("id", is(2)),
                                hasProperty("firstName", is("andrey")),
                                hasProperty("lastName", is("vasiliev")),
                                hasProperty("salary", is(600d))
                        )
                )))
        ;
    }

    @Test
    public void shouldOpenEditEmployeePageById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/1")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employee"))
                .andExpect(model().attribute("isNew", is(false)))
                .andExpect(model().attribute("employee", hasProperty("id", is(1))))
                .andExpect(model().attribute("employee", hasProperty("firstName", is("vlad"))))
                .andExpect(model().attribute("employee", hasProperty("lastName", is("zhuravlev"))))
                .andExpect(model().attribute("employee", hasProperty("salary", is(500d))))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("SEO"))
                        )
                )));
    }

    @Test
    public void shouldReturnToEmployeesPageIfEmployeeNotFoundById() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/99999")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.redirectedUrl("employees"));
    }

    @Test
    public void shouldUpdateEmployeeAfterEdit() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("first-name");
        employee.setLastName("last-name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employee/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("id", String.valueOf(employee.getDepartmentId()))
                        .param("firstName", employee.getFirstName())
                        .param("lastName", employee.getLastName())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("departmentId", String.valueOf(employee.getDepartmentId()))
                        .sessionAttr("employee", employee)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void shouldOpenNewEmployeePage() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("employee"))
                .andExpect(model().attribute("isNew", is(true)))
                .andExpect(model().attribute("employee", isA(Employee.class)))
                .andExpect(model().attribute("departments", hasItem(
                        allOf(
                                hasProperty("id", is(1)),
                                hasProperty("name", is("SEO"))
                        )
                )));
    }

    @Test
    public void shouldAddNewEmployee() throws Exception {

        Employee employee = new Employee();
        employee.setId(1);
        employee.setFirstName("first-name");
        employee.setLastName("last-name");
        employee.setSalary(100d);
        employee.setDepartmentId(1);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/employee")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("firstName", employee.getFirstName())
                        .param("lastName", employee.getLastName())
                        .param("salary", String.valueOf(employee.getSalary()))
                        .param("departmentId", String.valueOf(employee.getDepartmentId()))
                        .sessionAttr("employee", employee)
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }

    @Test
    public void shouldDeleteEmployee() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/employee/3/delete")
        ).andExpect(status().isFound())
                .andExpect(view().name("redirect:/employees"))
                .andExpect(redirectedUrl("/employees"));
    }
}
