package com.epam.web_app;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class EmployeeController {

    @GetMapping(value = "/employees")
    public final String employees() {
        return "employees";
    }

    @GetMapping(value = "/employee")
    public final String employee() {
        return "employee";
    }
}
