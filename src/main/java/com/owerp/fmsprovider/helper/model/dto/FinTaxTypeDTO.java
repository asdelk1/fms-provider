package com.owerp.fmsprovider.helper.model.dto;

import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FinTaxTypeDTO {

    private Long id;
    private String taxCode;
    private Double taxRate;
    private String description;
    private Boolean status = true;
    private LedgerAccountDTO controlAccount;
}
