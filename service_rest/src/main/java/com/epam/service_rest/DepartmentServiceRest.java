package com.epam.service_rest;

import com.epam.model.Department;
import com.epam.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DepartmentServiceRest implements DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public DepartmentServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Department> getAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Department>) responseEntity.getBody();
    }

    @Override
    public Optional<Department> getById(Integer departmentId) {

        LOGGER.debug("findById({})", departmentId);
        ResponseEntity<Department> responseEntity =
                restTemplate.getForEntity(url + "/" + departmentId, Department.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Department add(Department department) {

        LOGGER.debug("create({})", department);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, department, Department.class);
        return  (Department)responseEntity.getBody();
    }

    @Override
    public Department update(Department newDepartment, Integer id) {
        LOGGER.debug("update({})", newDepartment);
        // restTemplate.put(url, department);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(newDepartment, headers);
        ResponseEntity<Department> result = restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity, Department.class);
        return result.getBody();
    }


    @Override
    public void delete(Integer departmentId) {

        LOGGER.debug("delete({})", departmentId);
        //restTemplate.delete(url + "/" + departmentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Department> entity = new HttpEntity<>(headers);
        restTemplate.exchange(url + "/" + departmentId, HttpMethod.DELETE, entity, Integer.class);
    }
}
