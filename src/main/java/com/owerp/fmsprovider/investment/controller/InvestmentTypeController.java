package com.owerp.fmsprovider.investment.controller;

import com.owerp.fmsprovider.investment.model.data.InvestmentType;
import com.owerp.fmsprovider.investment.model.dto.InvestmentTypeDTO;
import com.owerp.fmsprovider.investment.service.InvestmentTypeService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/investments/types")
public class InvestmentTypeController {

    private final InvestmentTypeService service;
    private final EntityModelMapper mapper;

    public InvestmentTypeController(InvestmentTypeService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getAll() {
        List<InvestmentType> typeList = this.service.getAll();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(typeList)));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive() {
        List<InvestmentType> typeList = this.service.getAllActive();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(typeList)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getAllActive(@PathVariable long id) {
        InvestmentType type = this.service.get(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(type)));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> createType(@RequestBody InvestmentTypeDTO dto){
        InvestmentType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, this.map(type)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateType(@PathVariable long id, @RequestBody InvestmentTypeDTO dto){
        dto.setId(id);
        InvestmentType type = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, this.map(type)));
    }

    private List<InvestmentTypeDTO> map(List<InvestmentType> list) {
        return list.stream().map((InvestmentType t) -> this.mapper.getDTO(t, InvestmentTypeDTO.class)).collect(Collectors.toList());
    }


    private InvestmentTypeDTO map(InvestmentType entity){
        return this.mapper.getDTO(entity, InvestmentTypeDTO.class);
    }
}
