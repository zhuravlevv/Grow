package com.epam.service;

import com.epam.model.Department;
import com.epam.service.exception.GlobalServiceException;
import com.epam.service_api.DepartmentService;
import com.epam.service_api.ExcelService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service("departmentExcelService")
public class DepartmentExcelServiceImpl implements ExcelService {

    private final DepartmentService departmentService;

    public DepartmentExcelServiceImpl(@Qualifier("departmentServiceImpl") DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    @Transactional
    public void importExcel(MultipartFile multipartFile) {
        try {
            Workbook workbook = new XSSFWorkbook(multipartFile.getInputStream());
            Sheet sheet = workbook.getSheetAt(0);

            for (int index = 0; index < sheet.getPhysicalNumberOfRows(); index++) {
                if(index > 0){
                    Department department = new Department();
                    Row row = sheet.getRow(index);

                    department.setId((int)row.getCell(0).getNumericCellValue());
                    department.setName(row.getCell(1).getStringCellValue());

                    departmentService.add(department);
                }
            }
        }
        catch (IOException e) {
            throw new GlobalServiceException("Exception during excel importing", e);
        }
    }

    @Override
    public ByteArrayInputStream exportExcel() {
        String[] columns = {"id", "name"};
        Workbook workbook = new XSSFWorkbook();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Sheet sheet = workbook.createSheet("departments");

        Row title = sheet.createRow(0);

        for (int col = 0; col < columns.length; col++) {
            Cell cell = title.createCell(col);
            cell.setCellValue(columns[col]);
        }

        List<Department> departments = departmentService.getAll();

        int rowNumber = 1;
        for (Department department:
             departments) {
            Row row = sheet.createRow(rowNumber++);

            row.createCell(0).setCellValue(department.getId());
            row.createCell(1).setCellValue(department.getName());

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
