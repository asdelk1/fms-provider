package com.owerp.fmsprovider.customer.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerItemFormulaDTO {

    private Long id;
    private Double fromAmount;
    private Double toAmount;
    private Double amount;
    private Double percentage;
    private Double graterThanAmount;
    private Double perUnitAmount;
    private Double perUnitValue;
    private Boolean greaterThan = false;
}
