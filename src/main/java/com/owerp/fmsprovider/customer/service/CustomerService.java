package com.owerp.fmsprovider.customer.service;

import com.owerp.fmsprovider.customer.data.dto.CustomerDTO;
import com.owerp.fmsprovider.customer.data.model.Customer;
import com.owerp.fmsprovider.customer.data.model.CustomerType;
import com.owerp.fmsprovider.customer.repository.CustomerRepository;
import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import com.owerp.fmsprovider.supplier.service.FinNumbersService;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository repo;
    private final EntityModelMapper mapper;
    private final FinNumbersService finNumberService;
    private final CustomerTypeService customerTypeService;

    public CustomerService(CustomerRepository repo, EntityModelMapper mapper, FinNumbersService finNumberService, CustomerTypeService customerTypeService) {
        this.repo = repo;
        this.mapper = mapper;
        this.finNumberService = finNumberService;
        this.customerTypeService = customerTypeService;
    }
    
    public List<Customer> getAll(){
        return this.repo.findAll();
    }

    public List<Customer> getAllActive(){
        return this.repo.getCustomerByStatusIsTrue();
    }

    public Optional<Customer> get(Long id){
        return this.repo.findById(id);
    }

    public Customer save(CustomerDTO dto){
        Customer customer = this.mapper.getEntity(dto, Customer.class);
        if(customer.getId() == null){
            customer.setCustomerCode(this.generateCustomerCode(null, dto.getCustomerType().getId(), false));
        }

        return this.repo.save(customer);
    }

    public String generateCustomerCode(Long customerId, Long customerTypeId, Boolean isDisplayOnly) throws EntityNotFoundException {

            if(customerId == null) {
                Long nextNumber = finNumberService.getNextNumber(NumTypes.CUSTOMER, isDisplayOnly);
                CustomerType customerType = customerTypeService.get(customerTypeId);
                return customerType.getTypeCode() + "-" + nextNumber;
            }else{
                /* for existing customer, if customer type change only change the Item Code and keep the Number*/
                String customerCode = this.get(customerId).map(Customer::getCustomerCode).orElseThrow(() -> new EntityNotFoundException("Customer", customerId));
                String existingNumber = customerCode.split("-")[1];
                CustomerType customerType = customerTypeService.get(customerTypeId);
                return customerType.getTypeCode() + "-" + existingNumber;
            }

    }
}
