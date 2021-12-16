package com.owerp.fmsprovider.ledger.service;

import com.owerp.fmsprovider.ledger.model.data.LedgerTypes;
import com.owerp.fmsprovider.ledger.repository.LedgerTypesRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerTypesService {

    private final LedgerTypesRepository repo;

    public LedgerTypesService(LedgerTypesRepository repo) {
        this.repo = repo;
    }

    @PostConstruct
    private void initLedgerTypes() {

        List<LedgerTypes> list = this.repo.findAll();
        if (list.size() > 0) {
            return;
        }

        list = new ArrayList<>();

        list.add(new LedgerTypes(false, 1, 1, "Revenue"));
        list.add(new LedgerTypes(false, 1, 1, "Other Income"));
        list.add(new LedgerTypes(false, 1, 1, "Expenses"));
        list.add(new LedgerTypes(false, 1, 1, "Tax"));
        list.add(new LedgerTypes(false, 1, 1, "Realize Ex. Gain"));
        list.add(new LedgerTypes(false, 1, 1, "Realize Ex. Loss"));
        list.add(new LedgerTypes(false, 1, 1, "Unrealize Ex. Gain"));
        list.add(new LedgerTypes(false, 1, 1, "Unrealize Ex. Loss"));
        list.add(new LedgerTypes(false, 1, 2, "Fixed Assets"));
        list.add(new LedgerTypes(false, 1, 2, "Other Non - Current Assets"));
        list.add(new LedgerTypes(false, 1, 2, "Accounts Receivable"));
        list.add(new LedgerTypes(false, 1, 2, "Inter Company"));
        list.add(new LedgerTypes(false, 1, 2, "Current Assets"));
        list.add(new LedgerTypes(false, 1, 2, "Petty Cash"));
        list.add(new LedgerTypes(false, 1, 2, "Equity"));
        list.add(new LedgerTypes(false, 1, 2, "Retained Earnings"));
        list.add(new LedgerTypes(false, 1, 2, "Loan"));
        list.add(new LedgerTypes(false, 1, 2, "Other Non Current Liabilities"));
        list.add(new LedgerTypes(false, 1, 2, "Accounts Payable"));
        list.add(new LedgerTypes(false, 1, 2, "Acc. Dep."));
        list.add(new LedgerTypes(false, 1, 2, "Inventories"));
        list.add(new LedgerTypes(false, 1, 2, "Prepayments"));
        list.add(new LedgerTypes(false, 1, 2, "Interest In suspenses"));
        list.add(new LedgerTypes(false, 1, 2, "Un Deposited Fund"));
        list.add(new LedgerTypes(false, 1, 2, "Bank"));
        list.add(new LedgerTypes(false, 1, 2, "Reserves"));
        list.add(new LedgerTypes(false, 1, 2, "Lease"));
        list.add(new LedgerTypes(false, 1, 2, "Current Liabilities"));
        list.add(new LedgerTypes(false, 1, 2, "Investment"));

        this.repo.saveAll(list);
    }

    public List<LedgerTypes> getAllByCategory(Integer category) {
        return this.repo.getLedgerTypesByCategory(category);
    }

}
