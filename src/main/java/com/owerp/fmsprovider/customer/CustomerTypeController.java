package com.owerp.fmsprovider.customer;

import com.owerp.fmsprovider.customer.data.dto.CustomerTypeDTO;
import com.owerp.fmsprovider.customer.data.model.CustomerType;
import com.owerp.fmsprovider.customer.service.CustomerTypeService;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customer-types")
public class CustomerTypeController {

    private final CustomerTypeService service;
    private final EntityModelMapper modelMapper;

    public CustomerTypeController(CustomerTypeService service, EntityModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse> list() {
        List<CustomerType> customerTypeList = this.service.getAll();
        ApiResponse res = new ApiResponse(HttpStatus.OK, customerTypeList.stream().map(customerType -> this.modelMapper.getDTO(customerType, CustomerTypeDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> listAllActive() {
        List<CustomerType> customerTypeList = this.service.getAllActive();
        ApiResponse res = new ApiResponse(HttpStatus.OK, customerTypeList.stream().map(customerType -> this.modelMapper.getDTO(customerType, CustomerTypeDTO.class)).collect(Collectors.toList()));
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable long id){
        CustomerType type = this.service.get(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.modelMapper.getDTO(type, CustomerTypeDTO.class)));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse> createType(@RequestBody CustomerTypeDTO type){
        CustomerType createdType = this.service.save(type);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, createdType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateType(@PathVariable long id, @RequestBody CustomerTypeDTO type){
        type.setId(id); //set the id in case client dto doesn't have the id
        CustomerType createdType = this.service.save(type);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, createdType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteType(@PathVariable long id){
        this.service.delete(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK));
    }
}
