package com.owerp.fmsprovider.helper.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AccountingPeriodDTO {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status = true;
}
