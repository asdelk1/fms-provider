package com.owerp.fmsprovider.ledger.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportData {

    Long ledgerAccountId;
    String fromDate;
    String toDate;
    Long customerId;
    Long supplierId;
}
