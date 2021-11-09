package com.owerp.fmsprovider.supplier.controller;

import com.owerp.fmsprovider.supplier.model.data.SupplierType;
import com.owerp.fmsprovider.supplier.model.dto.SupplierTypeDTO;
import com.owerp.fmsprovider.supplier.service.SupplierTypeService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.data.UserPermission;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.service.UserPermissionService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/suppliers/types")
public class SupplierTypeController {

    private final SupplierTypeService service;
    private final EntityModelMapper modelMapper;
    private final UserPermissionService permissionService;

    public SupplierTypeController(SupplierTypeService service, EntityModelMapper modelMapper, UserPermissionService permissionService) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.permissionService = permissionService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> list() {
        this.permissionService.hasPermission(UserPermission.SUPPLIER_TYPE_LIST);
        List<SupplierTypeDTO> list = this.service.getAll()
                .stream()
                .map(st -> this.modelMapper.getDTO(st, SupplierTypeDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        this.permissionService.hasPermission(UserPermission.SUPPLIER_TYPE_LIST);
        SupplierType type = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Supplier Type", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody SupplierTypeDTO dto){
        this.permissionService.hasPermission(UserPermission.SUPPLIER_TYPE_ADD);
        SupplierType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, type));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody SupplierTypeDTO dto){
        this.permissionService.hasPermission(UserPermission.SUPPLIER_TYPE_EDIT);
        dto.setId(id);
        SupplierType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, type));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getActiveTypes(){
        List<SupplierTypeDTO> list = this.service.getAllActive().stream().map(s -> this.modelMapper.getDTO(s, SupplierTypeDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }
}
