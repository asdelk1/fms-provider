package com.owerp.fmsprovider.ledger.controller;

import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.ledger.service.LedgerAccountService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/ledger-accounts")
public class LedgerAccountController {

    private final LedgerAccountService service;
    private final EntityModelMapper mapper;

    public LedgerAccountController(LedgerAccountService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> fetchAll(){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.getAll()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> fetchAllActive(){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.getAllActive()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> fetch(@PathVariable long id){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(
                this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Ledger Account", id))));
        return ResponseEntity.ok(res);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> create(@RequestBody LedgerAccountDTO account){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.save(account)));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody LedgerAccountDTO dto){
        LedgerAccount resource = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Ledger Account", id));
        dto.setId(resource.getId());
        ApiResponse response = new ApiResponse(HttpStatus.OK, this.map(this.service.save(dto)));
        return ResponseEntity.ok(response);
    }

    private LedgerAccountDTO map(LedgerAccount account){
        return this.mapper.getDTO(account, LedgerAccountDTO.class);
    }

    private List<LedgerAccountDTO> map(List<LedgerAccount> list){
        return list.stream().map(la -> this.mapper.getDTO(la, LedgerAccountDTO.class)).collect(Collectors.toList());
    }
}
