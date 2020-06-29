package com.epam.web_app;

import com.epam.service.DepartmentDtoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    public DepartmentController(DepartmentDtoService departmentDtoService) {
        this.departmentDtoService = departmentDtoService;
    }

    @GetMapping(value = "/departments")
    public final String departments(Model model) {
        LOGGER.debug("departments()");
        model.addAttribute("departments", departmentDtoService.getAllWithAvgSalary());
        return "departments";
    }

    @GetMapping(value = "/department")
    public final String gotoAddDepartmentPage(Model model) {
        return "department";
    }


}
