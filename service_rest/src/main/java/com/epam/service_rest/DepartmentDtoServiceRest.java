package com.epam.service_rest;

import com.epam.model.dto.DepartmentDto;
import com.epam.service_api.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DepartmentDtoServiceRest implements DepartmentDtoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDtoServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentDtoServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<DepartmentDto> getAllWithAvgSalary() {

        LOGGER.debug("findAllWithAvgSalary({})", url);
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<DepartmentDto>) responseEntity.getBody();
    }
}
