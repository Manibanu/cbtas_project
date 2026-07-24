package org.example1.project.controller;

import org.example1.project.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/admin")
public class ExcelController {

    @Autowired
    private ExcelService excelService;

    // This endpoint accepts an uploaded .xlsx file from Postman (or a frontend form)
    // and passes it to ExcelService to be read and saved into MySQL.
    @PostMapping("/import-questions")
    public ResponseEntity<String> importQuestions(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please upload a file.");
        }

        try {
            String result = excelService.importQuestions(file);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Import failed: " + e.getMessage());
        }
    }
}