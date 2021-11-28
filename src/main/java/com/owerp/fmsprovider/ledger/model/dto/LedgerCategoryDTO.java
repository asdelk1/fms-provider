package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerCategoryDTO {

    private Long id;
    private String accCode;
    private String accName;
    private Integer comId;
    private boolean status;
    private LedgerTypesDTO ledgerType;
}
