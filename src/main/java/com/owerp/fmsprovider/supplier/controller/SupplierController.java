package com.owerp.fmsprovider.supplier.controller;

import com.owerp.fmsprovider.supplier.model.data.Supplier;
import com.owerp.fmsprovider.supplier.model.dto.SupplierDTO;
import com.owerp.fmsprovider.supplier.service.SupplierService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService service;
    private final EntityModelMapper mapper;

    public SupplierController(SupplierService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listAll(){
        List<SupplierDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> listAllActive(){
        List<SupplierDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        Supplier supplier = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Supplier", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(supplier, SupplierDTO.class)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody SupplierDTO dto){
        Supplier supplier = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(supplier, SupplierDTO.class)));
    }

    private List<SupplierDTO> map(List<Supplier> list){
        return list.stream().map(s -> this.mapper.getDTO(s, SupplierDTO.class)).collect(Collectors.toList());
    }
}
