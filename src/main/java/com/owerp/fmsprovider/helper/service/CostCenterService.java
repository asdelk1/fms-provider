package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.helper.repository.CostCenterRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CostCenterService {
    
    private final CostCenterRepository repo;
    private final EntityModelMapper mapper;

    public CostCenterService(CostCenterRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<CostCenter> getAll() {
        return this.repo.findAll();
    }

    public List<CostCenter> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<CostCenter> get(Long id){
        return this.repo.findById(id);
    }

    public CostCenter save(CostCenterDTO dto){
        CostCenter center = this.mapper.getEntity(dto, CostCenter.class);
        return this.repo.save(center);
    }
}
