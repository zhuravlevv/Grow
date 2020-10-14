package com.epam.rest;

import com.epam.model.dto.DepartmentDto;
import com.epam.service_api.DepartmentDtoService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)

public class DepartmentDtoControllerTest {
    @InjectMocks
    private DepartmentDtoController departmentDtoController;

    @Mock
    private DepartmentDtoService departmentDtoService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(departmentDtoController)
                .build();
    }

    @AfterEach
    public void end() {
        Mockito.verifyNoMoreInteractions(departmentDtoService);
    }

    @Test
    public void shouldFindAllWithAvgSalary() throws Exception {

        Mockito.when(departmentDtoService.getAllWithAvgSalary()).thenReturn(Arrays.asList(create(0), create(1)));

        mockMvc.perform(
                MockMvcRequestBuilders.get("/department_avg")
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("d0")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].averageSalary", Matchers.is(100)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name", Matchers.is("d1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].averageSalary", Matchers.is(101)))
        ;

        Mockito.verify(departmentDtoService).getAllWithAvgSalary();
    }

    private DepartmentDto create(int index) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(index);
        departmentDto.setName("d" + index);
        departmentDto.setAverageSalary(BigDecimal.valueOf(100 + index));
        return departmentDto;
    }
}
