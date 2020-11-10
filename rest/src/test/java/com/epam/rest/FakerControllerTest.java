package com.epam.rest;

import com.epam.service_api.FakerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FakerControllerTest {

    public static final String FAKER_ENDPOINT = "/gen";

    private MockMvc mockMvc;

    @InjectMocks
    private FakerController fakerController;

    @Mock
    private FakerService fakerService;

    @BeforeEach
    public void before(){
        mockMvc = MockMvcBuilders.standaloneSetup(fakerController)
                .build();
    }

    @Test
    public void testGenerate() throws Exception {
        int amount = 5;
        mockMvc.perform(
                MockMvcRequestBuilders.get(FAKER_ENDPOINT + "?amount=" + amount)
        ).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(content().string("generated " + amount));
    }

}
