package com.owerp.fmsprovider.ledger.controller;

import com.owerp.fmsprovider.ledger.service.LedgerTypesService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/master-data/ledger-types")
public class LedgerTypesController {

    private final LedgerTypesService service;

    public LedgerTypesController(LedgerTypesService service) {
        this.service = service;
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<ApiResponse> getAllByCategory(@PathVariable int category) {
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.service.getAllByCategory(category));
        return ResponseEntity.ok(res);
    }
}
