package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
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
@RequestMapping("/master-data/cost-centers")
public class CostCenterController {

    private final CostCenterService service;
    private final EntityModelMapper mapper;

    public CostCenterController(CostCenterService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> fetchAll() {
        List<CostCenterDTO> list = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> fetchAllActive() {
        List<CostCenterDTO> list = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> fetch(@PathVariable long id) {
        CostCenterDTO type = this.service.get(id).map(c -> this.mapper.getDTO(c, CostCenterDTO.class)).orElseThrow(() -> new EntityNotFoundException("Cost Center", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CostCenterDTO dto){
        CostCenter type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.mapper.getDTO(type, CostCenterDTO.class)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody CostCenterDTO dto){
        CostCenter extEntity = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Cost Center", id));
        dto.setId(id);
        CostCenter type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, type));
    }

    private List<CostCenterDTO> map(List<CostCenter> list) {
        return list.stream().map(c -> this.mapper.getDTO(c, CostCenterDTO.class)).collect(Collectors.toList());
    }
}
