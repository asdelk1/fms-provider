package com.owerp.fmsprovider.ledger.controller;

import com.owerp.fmsprovider.ledger.model.dto.ChartOfAccount;
import com.owerp.fmsprovider.ledger.service.ChartOfAccountService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/master-data/chart-of-accounts")
public class ChartOfAccountController {

    private final ChartOfAccountService service;

    public ChartOfAccountController(ChartOfAccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getChartOfAccounts(){
        List<ChartOfAccount> list = this.service.listChartOfAccount();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }
}
