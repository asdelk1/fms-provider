package com.owerp.fmsprovider.ledger.service;

import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.ledger.repository.LedgerAccountRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LedgerAccountService {

    private final LedgerAccountRepository repo;
    private final EntityModelMapper mapper;

    public LedgerAccountService(LedgerAccountRepository repo, EntityModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
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

    public LedgerAccount save(LedgerAccountDTO dto){
        LedgerAccount account = this.mapper.getEntity(dto, LedgerAccount.class);
        return this.repo.save(account);
    }
}
