package com.epam.web_app;

import com.epam.model.Department;
import com.epam.service.DepartmentDtoService;
import com.epam.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentDtoService departmentDtoService, DepartmentService departmentService) {
        this.departmentDtoService = departmentDtoService;
        this.departmentService = departmentService;
    }

    @GetMapping("/departments")
    public final String departments(Model model) {
        LOGGER.debug("departments()");
        model.addAttribute("departments", departmentDtoService.getAllWithAvgSalary());
        return "departments";
    }

    @GetMapping("/department")
    public final String gotoAddDepartmentPage(Model model) {
        return "department";
    }

    @GetMapping("department/{id}")
    public final String gotoEditDepartmentPage(@PathVariable Integer id, Model model){
        LOGGER.debug("gotoEditDepartmentPage({}, {})", id, model);
        Optional<Department> optionalDepartment = departmentService.getById(id);
        if(optionalDepartment.isPresent()){
            Department department = optionalDepartment.get() ;
            System.out.println(department);
            model.addAttribute("department", department);
            return "department";
        }
        return "redirect:departments";
    }

}
