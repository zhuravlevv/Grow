package com.epam.service;

import com.epam.model.Department;
import com.epam.model.Employee;
import com.epam.service_api.DepartmentService;
import com.epam.service_api.EmployeeService;
import com.epam.service_api.FakerService;
import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class FakerServiceImpl implements FakerService {

    private Faker faker = new Faker();

    private final DepartmentService departmentService;
    private final EmployeeService employeeService;

    public FakerServiceImpl(@Qualifier("departmentServiceImpl") DepartmentService departmentService,
                            @Qualifier("employeeServiceImpl") EmployeeService employeeService) {
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @Override
    public void generate(Integer amount) {
        Set<String> departmentNames = new HashSet<>();
        List<Department> alreadyExist = departmentService.getAll();
        int startCount = alreadyExist.size();
        alreadyExist.forEach(department -> {
            departmentNames.add(department.getName());
        });
        int count = 0;
        while (departmentNames.size() != amount + startCount && count < 100){
            String departmentName = faker.resolve("commerce.department");
            if(!departmentNames.contains(departmentName)){

                Department department = new Department(departmentName);
                Department addedDepartment = departmentService.add(department);

                for (int i = 0; i < 3; i++) {
                    Name name = faker.name();
                    String firstName = name.firstName();
                    String lastName = name.lastName();
                    double salary = faker.random().nextDouble() * 1000;
                    Employee employee = new Employee(firstName, lastName, salary, addedDepartment.getId());
                    employeeService.add(employee);
                }
            }
            departmentNames.add(departmentName);
            count++;
        }
    }
}
