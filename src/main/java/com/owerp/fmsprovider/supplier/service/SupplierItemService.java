package com.owerp.fmsprovider.supplier.service;

import com.owerp.fmsprovider.supplier.model.dto.SupplierItemDTO;
import com.owerp.fmsprovider.supplier.model.data.SupplierItem;
import com.owerp.fmsprovider.supplier.repository.SupplierItemRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierItemService {

    private final SupplierItemRepository repo;
    private final EntityModelMapper modelMapper;

    public SupplierItemService(SupplierItemRepository repo, EntityModelMapper modelMapper) {
        this.repo = repo;
        this.modelMapper = modelMapper;
    }

    public List<SupplierItem> getAll(){
        return this.repo.findAll();
    }

    public Optional<SupplierItem> get(Long id){
        return this.repo.findById(id);
    }

    public List<SupplierItem> getAllActive(){
        return this.repo.findAllByState(true);
    }

    public SupplierItem save(SupplierItemDTO dto){
        SupplierItem item = this.modelMapper.getEntity(dto, SupplierItem.class);
        return this.repo.save(item);
    }
}
