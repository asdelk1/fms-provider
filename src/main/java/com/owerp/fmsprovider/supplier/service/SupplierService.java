package com.owerp.fmsprovider.supplier.service;

import com.owerp.fmsprovider.supplier.model.data.Supplier;
import com.owerp.fmsprovider.supplier.model.dto.SupplierDTO;
import com.owerp.fmsprovider.supplier.repository.SupplierRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierService {

    private final SupplierRepository repo;
    private final EntityModelMapper mapper;

    public SupplierService(SupplierRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<Supplier> getAll(){
        return this.repo.findAll();
    }

    public Optional<Supplier> get(Long id){
        return this.repo.findById(id);
    }

    public Supplier save(final SupplierDTO dto){
        Supplier supplier = this.mapper.getEntity(dto, Supplier.class);
        return new Supplier();
    }
}
