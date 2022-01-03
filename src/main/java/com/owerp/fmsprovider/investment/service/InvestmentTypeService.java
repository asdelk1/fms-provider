package com.owerp.fmsprovider.investment.service;

import com.owerp.fmsprovider.investment.model.data.InvestmentType;
import com.owerp.fmsprovider.investment.model.dto.InvestmentTypeDTO;
import com.owerp.fmsprovider.investment.repository.InvestmentTypeRepository;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvestmentTypeService {

    private final InvestmentTypeRepository repo;
    private final EntityModelMapper mapper;

    public InvestmentTypeService(InvestmentTypeRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<InvestmentType> getAll(){
        return this.repo.findAll();
    }

    public List<InvestmentType> getAllActive(){
        return this.repo.findAllByActiveIsTrue();
    }

    public InvestmentType get(Long id) throws EntityNotFoundException {
        return this.repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Investment Type", id));
    }

    public InvestmentType save(InvestmentTypeDTO dto){
        InvestmentType type = this.mapper.getEntity(dto, InvestmentType.class);
        return this.repo.save(type);
    }
}
