package com.owerp.fmsprovider.customer.service;

import com.owerp.fmsprovider.customer.data.dto.CustomerTypeDTO;
import com.owerp.fmsprovider.customer.data.model.CustomerType;
import com.owerp.fmsprovider.customer.repository.CustomerTypeRepository;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerTypeService {

    private final CustomerTypeRepository repo;
    private final EntityModelMapper modelMapper;

    public CustomerTypeService(CustomerTypeRepository repo, EntityModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    public List<CustomerType> getAll() {
        return this.repo.findAll();
    }

    public CustomerType get(long id){
        Optional<CustomerType> type = this.repo.findById(id);
        return type.orElseThrow(() -> new EntityNotFoundException("Customer Type", id));
    }

    public CustomerType save(CustomerTypeDTO dto){
        CustomerType type = this.modelMapper.getEntity(dto, CustomerType.class);
        return this.repo.save(type);
    }

    public void delete(long id){
        CustomerType type = this.get(id);
        this.repo.delete(type);
    }
}
