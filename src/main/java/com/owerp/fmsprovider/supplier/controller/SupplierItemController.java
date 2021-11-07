package com.owerp.fmsprovider.supplier.controller;

import com.owerp.fmsprovider.supplier.model.dto.SupplierItemDTO;
import com.owerp.fmsprovider.supplier.model.data.SupplierItem;
import com.owerp.fmsprovider.supplier.service.SupplierItemService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suppliers/items")
public class SupplierItemController {

    private final SupplierItemService service;
    private final EntityModelMapper mapper;

    public SupplierItemController(SupplierItemService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> getAll(){
        List<SupplierItemDTO> list = this.service.getAll().stream().map(i -> mapper.getDTO(i, SupplierItemDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive(){
        List<SupplierItemDTO> list = this.service.getAllActive().stream().map(i -> mapper.getDTO(i, SupplierItemDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        SupplierItem item = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Supplier Item", id));
        SupplierItemDTO dto = this.mapper.getDTO(item, SupplierItemDTO.class);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, dto));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> create(@RequestBody SupplierItemDTO dto){
        SupplierItem item = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(item, SupplierItemDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody SupplierItemDTO dto){
        dto.setId(id);
        SupplierItem item = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(item, SupplierItemDTO.class)));
    }
}
