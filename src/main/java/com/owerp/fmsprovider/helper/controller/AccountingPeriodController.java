package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.AccountingPeriod;
import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.dto.AccountingPeriodDTO;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.helper.service.AccountingPeriodService;
import com.owerp.fmsprovider.helper.service.CostCenterService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/accounting-periods")
public class AccountingPeriodController {

    private final AccountingPeriodService service;
    private final EntityModelMapper mapper;

    public AccountingPeriodController(AccountingPeriodService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> fetchAll() {
        List<AccountingPeriodDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> fetchAllActive() {
        List<AccountingPeriodDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> fetch(@PathVariable long id) {
        AccountingPeriodDTO period = this.service.get(id).map(c -> this.mapper.getDTO(c, AccountingPeriodDTO.class)).orElseThrow(() -> new EntityNotFoundException("Cost Center", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, period));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody AccountingPeriodDTO dto){
        AccountingPeriod period = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(period, AccountingPeriodDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody AccountingPeriodDTO dto){
        AccountingPeriod extEntity = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Accounting Period", id));
        dto.setId(id);
        AccountingPeriod type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    private List<AccountingPeriodDTO> map(List<AccountingPeriod> list) {
        return list.stream().map(c -> this.mapper.getDTO(c, AccountingPeriodDTO.class)).collect(Collectors.toList());
    }
    
}
