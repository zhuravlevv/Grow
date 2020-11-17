package com.epam.service_api;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

public interface ExcelService {

    public void importExcel(MultipartFile multipartFile);

    public ByteArrayInputStream exportExcel();
}
