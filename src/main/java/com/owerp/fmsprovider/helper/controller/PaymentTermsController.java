package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import com.owerp.fmsprovider.helper.model.dto.CurrencyTypeDTO;
import com.owerp.fmsprovider.helper.model.dto.PaymentTermsDTO;
import com.owerp.fmsprovider.helper.service.PaymentTermsService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/payment-terms")
public class PaymentTermsController {

    private final PaymentTermsService service;
    private final EntityModelMapper mapper;

    public PaymentTermsController(PaymentTermsService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> fetchAll() {
        List<PaymentTermsDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> fetchAllActive() {
        List<PaymentTermsDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> fetch(@PathVariable long id) {
        PaymentTermsDTO type = this.service.get(id).map(c -> this.mapper.getDTO(c, PaymentTermsDTO.class)).orElseThrow(() -> new EntityNotFoundException("Payment Terms", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PaymentTermsDTO dto){
        PaymentTerms terms = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(terms, CurrencyTypeDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody PaymentTermsDTO dto){
        dto.setId(id);
        PaymentTerms terms = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, terms));
    }

    private List<PaymentTermsDTO> map(List<PaymentTerms> list) {
        return list.stream().map(c -> this.mapper.getDTO(c, PaymentTermsDTO.class)).collect(Collectors.toList());
    }
}
