package com.owerp.fmsprovider.customer.contoller;

import com.owerp.fmsprovider.customer.data.dto.CustomerItemDTO;
import com.owerp.fmsprovider.customer.data.model.CustomerItem;
import com.owerp.fmsprovider.customer.service.CustomerItemService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers/items")
public class CustomerItemController {

    private final CustomerItemService service;
    private final EntityModelMapper mapper;

    public CustomerItemController(CustomerItemService service, EntityModelMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll(){
        List<CustomerItemDTO> dtoList = this.map(this.service.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, dtoList));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive(){
        List<CustomerItemDTO> dtoList = this.map(this.service.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, dtoList));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        CustomerItem item = this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Customer Item", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(item)));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> create(@RequestBody CustomerItemDTO dto){
        CustomerItem item = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(item)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody CustomerItemDTO dto){
        this.service.get(id).orElseThrow(() -> new EntityNotFoundException("Customer Item", id));
        CustomerItem item = this.service.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.map(item)));
    }

    private CustomerItemDTO map(CustomerItem item){
        return this.mapper.getDTO(item, CustomerItemDTO.class);
    }

    private List<CustomerItemDTO> map(List<CustomerItem> list){
        return list.stream().map((i) -> this.mapper.getDTO(i, CustomerItemDTO.class)).collect(Collectors.toList());
    }
}
