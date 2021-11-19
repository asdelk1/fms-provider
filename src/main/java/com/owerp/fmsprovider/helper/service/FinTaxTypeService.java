package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.FinTaxType;
import com.owerp.fmsprovider.helper.model.dto.FinTaxTypeDTO;
import com.owerp.fmsprovider.helper.repository.FinTaxTypeRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FinTaxTypeService {

    private final FinTaxTypeRepository repo;
    private final EntityModelMapper mapper;

    public FinTaxTypeService(FinTaxTypeRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<FinTaxType> getAll() {
        return this.repo.findAll();
    }

    public List<FinTaxType> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<FinTaxType> get(Long id) {
        return this.repo.findById(id);
    }

    public FinTaxType save(FinTaxTypeDTO dto) {
        FinTaxType type = this.mapper.getEntity(dto, FinTaxType.class);
        return this.repo.save(type);
    }
}
