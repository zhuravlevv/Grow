package com.epam.service;

import com.epam.model.Department;
import com.epam.model.Employee;
import com.epam.service.exception.GlobalServiceException;
import com.epam.service_api.EmployeeService;
import com.epam.service_api.ExcelService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service("employeeExcelService")
public class EmployeeExcelServiceImpl implements ExcelService {

    private final EmployeeService employeeService;

    public EmployeeExcelServiceImpl(@Qualifier("employeeServiceImpl") EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void importExcel(MultipartFile multipartFile) {
        try {
            Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int index = 0; index < sheet.getPhysicalNumberOfRows(); index++) {
                if(index > 0){
                    Employee employee = new Employee();
                    Row row = sheet.getRow(index);

                    employee.setId((int)row.getCell(0).getNumericCellValue());
                    employee.setFirstName(row.getCell(1).getStringCellValue());
                    employee.setLastName(row.getCell(2).getStringCellValue());
                    employee.setSalary(row.getCell(3).getNumericCellValue());
                    employee.setDepartmentId((int)row.getCell(4).getNumericCellValue());

                    employeeService.add(employee);
                }
            }
        }
        catch (IOException e) {
            throw new GlobalServiceException("Exception during excel importing", e);
        }
    }

    @Override
    public ByteArrayInputStream exportExcel() {
        String[] columns = {"id", "firstName", "lastName", "salary", "departmentId"};
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("departments");

        Row title = sheet.createRow(0);

        for (int col = 0; col < columns.length; col++) {
            Cell cell = title.createCell(col);
            cell.setCellValue(columns[col]);
        }

        List<Employee> employees = employeeService.getAll();

        int rowNumber = 1;
        for (Employee employee:
                employees) {
            Row row = sheet.createRow(rowNumber++);

            row.createCell(0).setCellValue(employee.getId());
            row.createCell(1).setCellValue(employee.getFirstName());
            row.createCell(2).setCellValue(employee.getLastName());
            row.createCell(3).setCellValue(employee.getSalary());
            row.createCell(4).setCellValue(employee.getDepartmentId());

        }
        try {
            workbook.write(out);
        }
        catch (IOException e){
            throw new GlobalServiceException("Couldn't write output stream to workbook", e);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
