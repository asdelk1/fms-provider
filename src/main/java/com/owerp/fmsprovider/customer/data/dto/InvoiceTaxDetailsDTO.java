package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.helper.model.dto.FinTaxTypeDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvoiceTaxDetailsDTO {

    private Long id;
    private FinTaxTypeDTO finTaxType;
    private Double taxAmount;
}
