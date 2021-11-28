package com.owerp.fmsprovider.ledger.service;

import com.owerp.fmsprovider.ledger.model.data.LedgerCategory;
import com.owerp.fmsprovider.ledger.model.dto.ChartOfAccount;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.ledger.model.dto.LedgerCategoryDTO;
import com.owerp.fmsprovider.ledger.repository.LedgerAccountRepository;
import com.owerp.fmsprovider.ledger.repository.LedgerCategoryRepository;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChartOfAccountService {

    private final LedgerAccountRepository ledgerAccountRepository;
    private final LedgerCategoryRepository ledgerCategoryRepository;
    private final EntityModelMapper mapper;

    public ChartOfAccountService(LedgerCategoryRepository ledgerCategoryRepository, EntityModelMapper mapper, LedgerAccountRepository ledgerAccountRepository) {
        this.ledgerCategoryRepository = ledgerCategoryRepository;
        this.mapper = mapper;
        this.ledgerAccountRepository = ledgerAccountRepository;
    }

    public List<ChartOfAccount> listChartOfAccount() {
        List<ChartOfAccount> list = new ArrayList<>();

            for (LedgerCategory lc : ledgerCategoryRepository.findAll()) {
                ChartOfAccount coa = new ChartOfAccount();
                coa.setLedgerCategory(this.mapper.getDTO(lc, LedgerCategoryDTO.class));
                coa.setLedgerAccounts(ledgerAccountRepository.getAllByLedgerCategory(lc).stream().map((c) -> this.mapper.getDTO(c, LedgerAccountDTO.class)).collect(Collectors.toList()));
                list.add(coa);
            }
        return list;
    }
}
