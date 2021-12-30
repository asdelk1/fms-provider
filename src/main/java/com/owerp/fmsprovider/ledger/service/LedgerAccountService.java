package com.owerp.fmsprovider.ledger.service;

import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.ledger.model.data.LedgerCategory;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.ledger.model.dto.LedgerCategoryDTO;
import com.owerp.fmsprovider.ledger.repository.LedgerAccountRepository;
import com.owerp.fmsprovider.ledger.repository.LedgerCategoryRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LedgerAccountService {

    private final LedgerAccountRepository repo;
    private final LedgerCategoryRepository categoryRepository;
    private final EntityModelMapper mapper;

    public LedgerAccountService(LedgerAccountRepository repo, EntityModelMapper mapper, LedgerCategoryRepository categoryRepository) {
        this.repo = repo;
        this.mapper = mapper;
        this.categoryRepository = categoryRepository;
    }

    public List<LedgerAccount> getAll() {
        return this.repo.findAll();
    }

    public List<LedgerAccount> getAllActive() {
        return this.repo.findAllByStatusIsTrue();
    }

    public Optional<LedgerAccount> get(Long id) {
        return this.repo.findById(id);
    }

    public LedgerAccount save(LedgerAccountDTO dto) {
        LedgerAccount account = this.mapper.getEntity(dto, LedgerAccount.class);
        return this.repo.save(account);
    }

    public List<LedgerAccount> getAllByCategory(LedgerCategory category) {
        return this.repo.getAllByLedgerCategory(category);
    }

    public List<LedgerCategory> getAllActiveCategories() {
        return this.categoryRepository.findAllByStatusIsTrue();
    }

    public Optional<LedgerCategory> getLedgerCategory(long id) {
        return this.categoryRepository.findById(id);
    }

    public LedgerCategory saveLedgerCategory(LedgerCategoryDTO dto) {
        LedgerCategory category = this.mapper.getEntity(dto, LedgerCategory.class);
        return this.categoryRepository.save(category);
    }
}
