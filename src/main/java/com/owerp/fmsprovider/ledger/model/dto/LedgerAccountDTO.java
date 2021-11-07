package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerAccountDTO {
    private Long id;
    private String ledgerAccCode;
    private String ledgerAccName;
    private Boolean status = true;
    private LedgerCategoryDTO ledgerCategory;
}
