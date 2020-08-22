package com.epam.web_app;

import com.epam.model.Employee;
import com.epam.service_api.DepartmentService;
import com.epam.service_api.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Qualifier("departmentServiceRest")
    private final DepartmentService departmentService;

    @Qualifier("employeeServiceRest")
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(DepartmentService departmentService, EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/employees")
    public final String employees(Model model) {
        LOGGER.debug("employees()");
        model.addAttribute("employees", employeeService.getAll());
        return "employees";
    }

    @GetMapping(value = "/employee/{id}")
    public final String gotoEditEmployeePage(@PathVariable Integer id, Model model) {

        LOGGER.debug("gotoEditEmployeePage({},{})", id, model);
        Optional<Employee> optionalEmployee = employeeService.getById(id);
        if (optionalEmployee.isPresent()) {
            model.addAttribute("isNew", false);
            model.addAttribute("employee", optionalEmployee.get());
            model.addAttribute("departments", departmentService.getAll());
            return "employee";
        } else {
            return "redirect:employees";
        }
    }

    @PostMapping(value = "/employee/{id}")
    public String updateEmployee(@PathVariable Integer id, @Valid Employee employee,
                                 BindingResult result) {

        LOGGER.debug("updateEmployee({}, {})", employee, result);
        if (result.hasErrors()) {
            return "employee";
        } else {
            try {
                employeeService.update(employee, id);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            return "redirect:/employees";
        }
    }

    @GetMapping(value = "/employee")
    public final String gotoAddEmployeePage(Model model) {

        LOGGER.debug("gotoAddEmployeePage({})", model);
        Employee employee = new Employee();
        employee.setDepartmentId(1);
        model.addAttribute("isNew", true);
        model.addAttribute("employee", employee);
        model.addAttribute("departments", departmentService.getAll());
        return "employee";
    }

    @PostMapping(value = "/employee")
    public String addEmployee(@Valid Employee employee,
                              BindingResult result) {

        LOGGER.debug("addEmployee({}, {})", employee, result);
        if (result.hasErrors()) {
            return "employee";
        } else {
            try {
                employeeService.add(employee);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return "redirect:/employees";
        }
    }

    @GetMapping(value = "/employee/{id}/delete")
    public final String deleteEmployeeById(@PathVariable Integer id, Model model) {

        LOGGER.debug("delete({},{})", id, model);
        employeeService.delete(id);
        return "redirect:/employees";
    }

}
