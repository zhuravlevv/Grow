package com.epam.rest.exception;

import com.epam.service.exception.DepartmentNotFoundException;
import com.epam.service.exception.EmployeeNotFoundException;
import com.epam.service.exception.ErrorResponse;
import com.epam.service.exception.GlobalServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    private static final String DEPARTMENT_NOT_FOUND = "Department not found";
    private static final String EMPLOYEE_NOT_FOUND = "Employee not found";
    private static final String VALIDATION_ERROR = "Validation error";

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(DepartmentNotFoundException.class)
    public final ResponseEntity<ErrorResponse> handleUserNotFoundException (DepartmentNotFoundException ex, WebRequest request) {
        LOGGER.error(DEPARTMENT_NOT_FOUND, ex);

        ErrorResponse errorResponse = new ErrorResponse(DEPARTMENT_NOT_FOUND, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(Exception ex, WebRequest request) {
        LOGGER.error(VALIDATION_ERROR, ex);

        ErrorResponse errorResponse = new ErrorResponse(VALIDATION_ERROR, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(GlobalServiceException.class)
    public ResponseEntity<ErrorResponse> handleGlobalServiceException(Exception ex){
        LOGGER.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), ex.getCause().getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleDepartmentNotFoundException(Exception ex){
        LOGGER.error(ex.getMessage(), ex);

        ErrorResponse errorResponse = new ErrorResponse(EMPLOYEE_NOT_FOUND, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
