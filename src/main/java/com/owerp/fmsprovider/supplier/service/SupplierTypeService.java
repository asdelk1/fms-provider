package com.owerp.fmsprovider.supplier.service;

import com.owerp.fmsprovider.supplier.model.dto.SupplierTypeDTO;
import com.owerp.fmsprovider.supplier.model.data.SupplierType;
import com.owerp.fmsprovider.supplier.repository.SupplierTypeRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierTypeService {

    private final SupplierTypeRepository repository;
    private final EntityModelMapper modelMapper;

    public SupplierTypeService(SupplierTypeRepository repository, EntityModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public List<SupplierType> getAll(){
        return this.repository.findAll();
    }

    public Optional<SupplierType> get(Long id){
        return this.repository.findById(id);
    }

    public SupplierType save(final SupplierTypeDTO dto){
        SupplierType type = this.modelMapper.getEntity(dto, SupplierType.class);
        return this.repository.save(type);
    }

    public List<SupplierType> getAllActive(){
        return this.repository.findAllByStatusIsTrue();
    }
}
