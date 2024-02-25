package com.spring.turnxcel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.turnxcel.converter.ConfigXmlToExcel;
import com.turnxcel.xmlreader.XmlParserLTE;

import java.io.ByteArrayOutputStream;
import java.util.Map;

@Controller
public class FileController {

    @Autowired
    private XmlParserLTE xmlParserLTE;

    @Autowired
    private ConfigXmlToExcel configXmlToExcel;

    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a file to upload");
        }

        try {
            Map<String, String> parameterData = xmlParserLTE.readXML(file);
            ByteArrayOutputStream outputStream = configXmlToExcel.writeExcel(parameterData);
            return ResponseEntity.ok()
                    .header("Content-Disposition", "attachment; filename=converted.xlsx")
                    .body(outputStream.toByteArray());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process file: " + e.getMessage());
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> downloadExcelFile(@RequestParam("file") byte[] excelBytes) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "converted.xlsx");
        headers.setContentLength(excelBytes.length);

        return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
    }
}