package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class GeneralLedger {

    private String transType;
    private LocalDateTime entryDate;
    private String entryNumber;
    private String details;
    private String oppAccounts;
    private double debitAmount;
    private double creditAmount;
    private double closingBalance;
}
