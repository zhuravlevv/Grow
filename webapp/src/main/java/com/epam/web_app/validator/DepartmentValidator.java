package com.epam.web_app.validator;

import com.epam.model.Department;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class DepartmentValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Department.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors, "name", "departmentName.empty");
        Department department = (Department) target;

        if (StringUtils.hasLength(department.getName())
                && department.getName().length() > 20) {
            errors.rejectValue("name", "departmentName.maxSize");
        }
    }
}
