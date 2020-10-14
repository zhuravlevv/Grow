package com.epam.service_rest.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@TestConfiguration
public class TestConfigRestService {

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections.singletonList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }

    @Bean
    public String departmentsUrl(){
        return "http://localhost:8080/department";
    }

    @Bean
    public String departmentDtosUrl(){
        return "http://localhost:8080/department_avg";
    }

    @Bean
    public String employeesUrl(){
        return "http://localhost:8080/employee";
    }
}
