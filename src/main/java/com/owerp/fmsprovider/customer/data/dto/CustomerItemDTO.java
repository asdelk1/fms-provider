package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.customer.data.enums.CustomerFormulaType;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class CustomerItemDTO {

    private Long id;
    private CustomerTypeDTO customerType;
    private String itemName;
    private String remarks;
    private Boolean status = true;
    private LedgerAccountDTO ledgerAccount;
    private CustomerFormulaType formulaType;
    private Double fixedAmount;
    private Double fixedPercentage;
    private Set<CustomerItemFormulaDTO> formulaSlab;
}
