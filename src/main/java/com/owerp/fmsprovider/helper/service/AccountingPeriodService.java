package com.owerp.fmsprovider.helper.service;

import com.owerp.fmsprovider.helper.model.data.AccountingPeriod;
import com.owerp.fmsprovider.helper.model.dto.AccountingPeriodDTO;
import com.owerp.fmsprovider.helper.repository.AccountingPeriodRepository;
import com.owerp.fmsprovider.system.advice.ApplicationException;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountingPeriodService {

    private final AccountingPeriodRepository repo;
    private final EntityModelMapper mapper;

    public AccountingPeriodService(AccountingPeriodRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    public List<AccountingPeriod> getAll() {
        return this.repo.findAll();
    }

    public List<AccountingPeriod> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<AccountingPeriod> get(Long id){
        return this.repo.findById(id);
    }

    public AccountingPeriod save(AccountingPeriodDTO dto){
        AccountingPeriod period = this.mapper.getEntity(dto, AccountingPeriod.class);

        if(period.getEndDate().isBefore(period.getStartDate()) || period.getEndDate().isEqual(period.getStartDate())){
            throw new ApplicationException("End Date should be after the Start Date");
        }

        return this.repo.save(period);
    }
    
}
