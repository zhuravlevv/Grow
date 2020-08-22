package com.epam.service_rest;

import com.epam.model.Employee;
import com.epam.service_api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceRest implements EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceRest.class);

    private String url;

    private RestTemplate restTemplate;

    public EmployeeServiceRest(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public List<Employee> getAll() {

        LOGGER.debug("findAll()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url, List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    @Override
    public List<Employee> getByDepartmentId(Integer departmentId) {

        LOGGER.debug("findByDepartmentId()");
        ResponseEntity responseEntity = restTemplate.getForEntity(url + "?departmentId=" + departmentId, List.class);
        return (List<Employee>) responseEntity.getBody();
    }

    @Override
    public Optional<Employee> getById(Integer employeeId) {

        LOGGER.debug("findById({})", employeeId);
        ResponseEntity<Employee> responseEntity =
                restTemplate.getForEntity(url + "/" + employeeId, Employee.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public Employee add(Employee employee) {

        LOGGER.debug("create({})", employee);
        ResponseEntity responseEntity = restTemplate.postForEntity(url, employee, Employee.class);
        Object result = responseEntity.getBody();
        return (Employee) result;
    }

    @Override
    public Employee update(Employee newEmployee, Integer id) {

        LOGGER.debug("update({})", newEmployee);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<Employee> entity = new HttpEntity<>(newEmployee, headers);
        System.out.println(entity);
        ResponseEntity<Employee> result = restTemplate.exchange(url + "/" + id, HttpMethod.PUT, entity, Employee.class);
        return result.getBody();
    }

    @Override
    public void delete(Integer employeeId) {

        LOGGER.debug("delete({})", employeeId);
        restTemplate.delete(url + "/" + employeeId);
    }
}
