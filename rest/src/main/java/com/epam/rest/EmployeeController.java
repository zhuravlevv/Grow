package com.epam.rest;

import com.epam.model.Employee;
import com.epam.service.exception.EmployeeNotFoundException;
import com.epam.service_api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

    @Qualifier("employeeServiceImpl")
    private final EmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAll(){
        LOGGER.trace("getAll()");

        return ResponseEntity.ok(employeeService.getAll());
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Integer id){
        LOGGER.trace("getById({})", id);

        return ResponseEntity.ok(employeeService.getById(id).orElseThrow(() -> new EmployeeNotFoundException(id)));
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> add(@RequestBody Employee employee) {
        LOGGER.trace("add({})", employee);

        Employee createdEmployee = employeeService.add(employee);
        return ResponseEntity.created(URI.create("employee/" + createdEmployee.getId()))
                .body(createdEmployee);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@RequestBody Employee newEmployee) {
        LOGGER.trace("update({})", newEmployee);

        return ResponseEntity.ok(employeeService.update(newEmployee));
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Integer id){
        LOGGER.trace("delete({})", id);

        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/employee_dep")
    public ResponseEntity<List<Employee>> getByDepartmentId(@RequestParam Integer id){
        LOGGER.trace("getByDepartmentId({})", id);

        return ResponseEntity.ok(employeeService.getByDepartmentId(id));
    }
}
