package com.epam.rest;

import com.epam.model.Department;
import com.epam.service.exception.DepartmentNotFoundException;
import com.epam.service_api.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Qualifier("departmentServiceImpl")
    private final DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public ResponseEntity<List<Department>> getAll(){
        LOGGER.trace("getAll()");

        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Department> getById(@PathVariable Integer id){
        LOGGER.trace("getById({})", id);

        return ResponseEntity.ok(departmentService.getById(id).orElseThrow(()->new DepartmentNotFoundException(id)));
    }

    @PostMapping
    public ResponseEntity<Department> add(@RequestBody Department department){
        LOGGER.trace("add({})", department);

        Department createdDepartment = departmentService.add(department);
        return ResponseEntity.created(URI.create("/deapartment/" + createdDepartment.getId()))
                .body(createdDepartment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Department> update(@RequestBody Department newDepartment){
        LOGGER.trace("update({})", newDepartment);

        return ResponseEntity.ok(departmentService.update(newDepartment));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Department> delete(@PathVariable Integer id){
        LOGGER.trace("delete({})", id);

        departmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
