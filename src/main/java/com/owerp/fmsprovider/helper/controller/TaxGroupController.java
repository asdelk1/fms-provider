package com.owerp.fmsprovider.helper.controller;

import com.owerp.fmsprovider.helper.model.data.TaxGroup;
import com.owerp.fmsprovider.helper.service.TaxGroupService;
import com.owerp.fmsprovider.supplier.model.dto.TaxGroupDTO;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/master-data/tax-groups")
public class TaxGroupController {

    private final TaxGroupService service;
    private final EntityModelMapper mapper;

    public TaxGroupController(TaxGroupService service, EntityModelMapper mapper) {
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
                this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Tax Group", id))
        ));
        return ResponseEntity.ok(res);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> create(@RequestBody TaxGroupDTO dto){
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.save(dto)));
        return ResponseEntity.ok(res);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody TaxGroupDTO dto){
        dto.setId(id);
        ApiResponse res = new ApiResponse(HttpStatus.OK, this.map(this.service.save(dto)));
        return ResponseEntity.ok(res);
    }

    private TaxGroupDTO map(TaxGroup group){
        return this.mapper.getDTO(group, TaxGroupDTO.class);
    }
    private List<TaxGroupDTO> mapList(List<TaxGroup> groups){
        return groups.stream().map(tg -> this.mapper.getDTO(tg, TaxGroupDTO.class)).collect(Collectors.toList());
    }

}
