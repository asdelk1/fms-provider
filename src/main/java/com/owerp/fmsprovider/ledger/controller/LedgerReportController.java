package com.owerp.fmsprovider.ledger.controller;


import com.owerp.fmsprovider.ledger.model.dto.ReportData;
import com.owerp.fmsprovider.ledger.service.LedgerReportService;
import com.owerp.fmsprovider.system.advice.ApplicationException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@RestController("/ledger-reports")
public class LedgerReportController {


    private LedgerReportService service;

    @PostMapping()
    public ResponseEntity<InputStream> generateLedgerReport(@RequestBody ReportData data) {

        try {
            File report = this.service.genGeneralLedgerReport(data);
            InputStream is = new FileInputStream(report);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(is);


        } catch (FileNotFoundException e) {
            String message = "File not found: " + e.getMessage();
            throw new ApplicationException(message);
        }
    }
}
