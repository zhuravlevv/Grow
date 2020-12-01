package com.epam.rest;

import com.epam.service_api.ExcelService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@RestController
@RequestMapping("/excel")
public class ExcelController {

    private final ExcelService departmentExcelService;
    private final ExcelService employeeExcelService;

    @Value("${xmls.file.employee}")
    private String employeeFileName;

    @Value("${xmls.file.department}")
    private String departmentFileName;

    public ExcelController(@Qualifier("departmentExcelService") ExcelService departmentExcelService, @Qualifier("employeeExcelService") ExcelService employeeExcelService) {
        this.departmentExcelService = departmentExcelService;
        this.employeeExcelService = employeeExcelService;
    }

    @PostMapping("/department")
    public ResponseEntity<?> importExcelDepartments(@RequestParam("file") MultipartFile multipartFile){
        departmentExcelService.importExcel(multipartFile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/department")
    public ResponseEntity<InputStreamResource> exportExcelDepartments(){
        ByteArrayInputStream in = departmentExcelService.exportExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + departmentFileName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(new InputStreamResource(in));
    }

    @PostMapping("/employee")
    public ResponseEntity<?> importExcelEmployees(@RequestParam("file") MultipartFile multipartFile){
        employeeExcelService.importExcel(multipartFile);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/employee")
    public ResponseEntity<InputStreamResource> exportExcelEmployees(){
        ByteArrayInputStream in = employeeExcelService.exportExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + employeeFileName);

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(new InputStreamResource(in));
    }


}
