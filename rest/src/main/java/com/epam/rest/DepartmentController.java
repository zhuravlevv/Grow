package com.epam.rest;

import com.epam.model.Department;
import com.epam.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class DepartmentController {

    private DepartmentService departmentService;

    private final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }


    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAll(){
        LOGGER.trace("getAll()");
        return ResponseEntity.ok(departmentService.getAll());
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getById(@PathVariable Integer id){
        LOGGER.trace("getById({})", id);
        try {
            return ResponseEntity.ok(departmentService.getById(id).orElseThrow(Exception::new));
        } catch (Exception e){
            e.printStackTrace();
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/department")
    public ResponseEntity<Department> add(@RequestBody Department department){
        LOGGER.trace("add({})", department);

        try {
            Department createdDepartment = departmentService.add(department);
            return ResponseEntity.created(URI.create("/deapartment/" + createdDepartment.getId()))
                    .body(createdDepartment);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.badRequest().body(null);
    }



}
