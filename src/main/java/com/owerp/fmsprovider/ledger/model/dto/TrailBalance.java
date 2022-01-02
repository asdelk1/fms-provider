package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrailBalance {

    private Long accId;
    private String subACName;
    private String subAcNumber;
    private Double debitAmount;
    private Double creditAmount;
}
