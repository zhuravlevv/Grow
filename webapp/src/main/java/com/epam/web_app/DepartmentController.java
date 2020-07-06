package com.epam.web_app;

import com.epam.model.Department;
import com.epam.service.DepartmentDtoService;
import com.epam.service.DepartmentService;
import com.epam.web_app.validator.DepartmentValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.lang.model.util.ElementScanner6;
import java.util.Optional;

@Controller
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    private final DepartmentDtoService departmentDtoService;

    private final DepartmentService departmentService;

    @Autowired
    DepartmentValidator departmentValidator;

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
        LOGGER.debug("gotoAddDepartmentPage({})", model);
        model.addAttribute("isNew", true);
        model.addAttribute("department", new Department());
        return "department";
    }

    @GetMapping("department/{id}")
    public final String gotoEditDepartmentPage(@PathVariable Integer id, Model model){
        LOGGER.debug("gotoEditDepartmentPage({}, {})", id, model);
        Optional<Department> optionalDepartment = departmentService.getById(id);
        if(optionalDepartment.isPresent()){
            Department department = optionalDepartment.get();
            model.addAttribute("isNew", false);
            model.addAttribute("department", department);
            return "department";
        }
        return "redirect:departments";
    }


    @PostMapping(value = "/department")
    public String addDepartment(Department department, BindingResult result) {

        LOGGER.debug("addDepartment({})", department);
        departmentValidator.validate(department, result);
        if(result.hasErrors()){
            return "department";
        }
        else {
            try {
                departmentService.add(department);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/departments";
        }
    }

    @GetMapping(value = "/department/{id}/delete")
    public final String deleteDepartmentById(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete({},{})", id, model);
        departmentService.delete(id);
        return "redirect:/departments";
    }

    @PostMapping(value = "/department/{id}")
    public String updateDepartment(@PathVariable Integer id, Department department, BindingResult result) {

        LOGGER.debug("updateDepartment({})", department);
        departmentValidator.validate(department, result);
        if(result.hasErrors()){
            return "department";
        }
        else{
            try {
                departmentService.update(department, id);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "redirect:/departments";
        }
    }

}
