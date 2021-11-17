package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.CurrencyType;
import com.owerp.fmsprovider.helper.model.dto.CurrencyTypeDTO;
import com.owerp.fmsprovider.helper.repository.CurrencyTypeRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CurrencyTypeService {

    private final CurrencyTypeRepository repo;
    private final EntityModelMapper mapper;


    public CurrencyTypeService(CurrencyTypeRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<CurrencyType> getAll() {
        return this.repo.findAll();
    }

    public List<CurrencyType> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<CurrencyType> get(Long id){
        return this.repo.findById(id);
    }

    public CurrencyType save(CurrencyTypeDTO dto){
        CurrencyType type = this.mapper.getEntity(dto, CurrencyType.class);
        return this.repo.save(type);
    }
}
