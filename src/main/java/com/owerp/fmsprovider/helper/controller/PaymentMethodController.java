package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.PaymentMethod;
import com.owerp.fmsprovider.helper.model.dto.PaymentMethodDTO;
import com.owerp.fmsprovider.helper.service.PaymentMethodService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/payment-methods")
public class PaymentMethodController {

    private final PaymentMethodService service;
    private final EntityModelMapper mapper;

    public PaymentMethodController(PaymentMethodService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        List<PaymentMethodDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive(){
        List<PaymentMethodDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        PaymentMethodDTO method = this.service.get(id).map(p -> this.mapper.getDTO(p, PaymentMethodDTO.class)).orElseThrow(() -> new EntityNotFoundException("Payment Method", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, method));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody PaymentMethodDTO dto){
        PaymentMethod method = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(method, PaymentMethod.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> create(@PathVariable long id, @RequestBody PaymentMethodDTO dto){
        dto.setId(id);
        PaymentMethod method = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(method, PaymentMethod.class)));
    }

    private List<PaymentMethodDTO> map(List<PaymentMethod> list){
        return list.stream().map(p -> this.mapper.getDTO(p, PaymentMethodDTO.class)).collect(Collectors.toList());
    }
}
