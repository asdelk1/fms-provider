package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ChartOfAccount {

    private LedgerCategoryDTO ledgerCategory;
    private List<LedgerAccountDTO> ledgerAccounts = new ArrayList<>();
}
