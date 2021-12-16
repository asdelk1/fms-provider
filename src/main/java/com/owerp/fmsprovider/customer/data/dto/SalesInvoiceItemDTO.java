package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.supplier.model.dto.TaxGroupDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesInvoiceItemDTO {

    private Long id;
    private CustomerItemDTO customerItem;
    private String itemDescription;
    private Double unitValue;
    private Double amount;
    private TaxGroupDTO taxGroup;
    private CostCenterDTO costCenter;
    private Double taxAmount;
}
