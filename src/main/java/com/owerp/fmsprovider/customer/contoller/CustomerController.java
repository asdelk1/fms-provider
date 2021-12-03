package com.owerp.fmsprovider.customer.contoller;

import com.owerp.fmsprovider.customer.data.dto.CustomerDTO;
import com.owerp.fmsprovider.customer.data.model.Customer;
import com.owerp.fmsprovider.customer.service.CustomerService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final EntityModelMapper mapper;


    public CustomerController(CustomerService customerService, EntityModelMapper mapper) {
        this.customerService = customerService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        List<CustomerDTO> list = this.map(this.customerService.getAll());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/active")
    public ResponseEntity<ApiResponse> getAllActive() {
        List<CustomerDTO> list = this.map(this.customerService.getAllActive());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> get(@PathVariable long id){
        CustomerDTO dto = this.customerService.get(id).map(this::map).orElseThrow(() -> new EntityNotFoundException("Customer", id));
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, dto));
    }

    @GetMapping("/{customerId}/{customerTypeId}/new-customer-code")
    public ResponseEntity<ApiResponse> getCustomerCode(@PathVariable long customerId, @PathVariable long customerTypeId){
        String no = this.customerService.generateCustomerCode(
                customerId == 0 ? null : customerId,
                customerTypeId,
                true
        );
        Map<String, String> res = new HashMap<String, String>();
        res.put("customerCode", no);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, res));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody CustomerDTO dto){
        Customer createdCustomer = this.customerService.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, this.map(createdCustomer)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable long id, @RequestBody CustomerDTO dto){
        this.customerService.get(id).orElseThrow(() -> new EntityNotFoundException("Customer", id));
        dto.setId(id);
        Customer createdCustomer = this.customerService.save(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, this.map(createdCustomer)));
    }

    private List<CustomerDTO> map(List<Customer> list) {
        return list.stream().map(c -> this.mapper.getDTO(c, CustomerDTO.class)).collect(Collectors.toList());
    }

    private CustomerDTO map(Customer entity) {
        return this.mapper.getDTO(entity, CustomerDTO.class);
    }
}
