package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LedgerTypesDTO {
    private Long id;
    private String typeName;
    private Integer typeCategory;
    private Integer clarification;
    private Integer status;
    private String clarifi;
}
