package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.FinTaxType;
import com.owerp.fmsprovider.helper.model.dto.FinTaxTypeDTO;
import com.owerp.fmsprovider.helper.service.FinTaxTypeService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/financial-tax-types")
public class FinTaxTypeController {

    private final FinTaxTypeService service;
    private final EntityModelMapper mapper;

    public FinTaxTypeController(FinTaxTypeService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.mapList(this.service.getAll()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive(){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.mapList(this.service.getAllActive()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(
                this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Financial Tax Type", id))
        ));
        return ResponseEntity.ok(res);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> create(@RequestBody FinTaxTypeDTO dto){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.save(dto)));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody FinTaxTypeDTO dto){
        dto.setId(id);
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.save(dto)));
        return ResponseEntity.ok(res);
    }

    private FinTaxTypeDTO map(FinTaxType group){
        return this.mapper.getDTO(group, FinTaxTypeDTO.class);
    }
    private List<FinTaxTypeDTO> mapList(List<FinTaxType> groups){
        return groups.stream().map(tg -> this.mapper.getDTO(tg, FinTaxTypeDTO.class)).collect(Collectors.toList());
    }
}
