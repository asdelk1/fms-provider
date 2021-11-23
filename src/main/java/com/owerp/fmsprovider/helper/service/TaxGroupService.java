package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.TaxGroup;
import com.owerp.fmsprovider.helper.repository.TaxGroupRepository;
import com.owerp.fmsprovider.supplier.model.dto.TaxGroupDTO;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaxGroupService {

    private final TaxGroupRepository repo;
    private final EntityModelMapper mapper;

    public TaxGroupService(TaxGroupRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<TaxGroup> getAll() {
        return this.repo.findAll();
    }

    public List<TaxGroup> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<TaxGroup> get(Long id) {
        return this.repo.findById(id);
    }

    public TaxGroup save(TaxGroupDTO dto) {
        TaxGroup group = this.mapper.getEntity(dto, TaxGroup.class);
        return this.repo.save(group);
    }
}
