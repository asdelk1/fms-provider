package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.CurrencyType;
import com.owerp.fmsprovider.helper.model.dto.CurrencyTypeDTO;
import com.owerp.fmsprovider.helper.service.CurrencyTypeService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/currency-types")
public class CurrencyTypeController {

    private final CurrencyTypeService service;
    private final EntityModelMapper mapper;

    public CurrencyTypeController(CurrencyTypeService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> fetchAll() {
        List<CurrencyTypeDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> fetchAllActive() {
        List<CurrencyTypeDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> fetch(@PathVariable long id) {
        CurrencyTypeDTO type = this.service.get(id).map(c -> this.mapper.getDTO(c, CurrencyTypeDTO.class)).orElseThrow(() -> new EntityNotFoundException("Currency Type", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CurrencyTypeDTO dto){
        CurrencyType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(type, CurrencyTypeDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody CurrencyTypeDTO dto){
        dto.setId(id);
        CurrencyType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    private List<CurrencyTypeDTO> map(List<CurrencyType> list) {
        return list.stream().map(c -> this.mapper.getDTO(c, CurrencyTypeDTO.class)).collect(Collectors.toList());
    }
}
