package com.epam.rest;

import com.epam.model.Employee;
import com.epam.rest.exception.EmployeeNotFoundException;
import com.epam.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class EmployeeController {

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
    public ResponseEntity<Employee> add(@RequestBody Employee employee){
        try {
            Employee createdEmployee = employeeService.add(employee);
            return ResponseEntity.created(URI.create("employee/" + createdEmployee.getId()))
                    .body(createdEmployee);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }

    @PostMapping("/employee/{id}")
    public ResponseEntity<Employee> update(@RequestBody Employee newEmployee,@PathVariable Integer id){
        if(newEmployee == null){
            return ResponseEntity.badRequest().body(null);
        }
        try {
            return ResponseEntity.ok(employeeService.update(newEmployee, id));
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> delete(@PathVariable Integer id){
        employeeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
