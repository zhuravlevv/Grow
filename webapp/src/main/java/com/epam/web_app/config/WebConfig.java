package com.epam.web_app.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Configuration
@ComponentScan(basePackages = {"com.epam.*"})
public class WebConfig {

    @Value("${point.departments}")
    private String departments;

    @Value("${point.department_dtos}")
    private String departmentDtos;

    @Value("${point.employees}")
    private String employees;

    @Value("${protocol}")
    private String protocol;

    @Value("${host}")
    private String host;

    @Value("${port}")
    private String port;

    @Bean
    public String departmentsUrl(){
        return protocol
                .concat("://").concat(host)
                .concat(":").concat(port)
                .concat("/").concat(departments);
    }

    @Bean
    public String departmentDtosUrl(){
        return protocol
                .concat("://").concat(host)
                .concat(":").concat(port)
                .concat("/").concat(departmentDtos);
    }

    @Bean
    public String employeesUrl(){
        return protocol
                .concat("://").concat(host)
                .concat(":").concat(port)
                .concat("/").concat(employees);
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setMessageConverters(Collections
                .singletonList(new MappingJackson2HttpMessageConverter()));
        return restTemplate;
    }


}
