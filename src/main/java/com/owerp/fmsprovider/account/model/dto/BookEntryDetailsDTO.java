package com.owerp.fmsprovider.account.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.owerp.fmsprovider.account.model.dto.BookEntryDTO;
import com.owerp.fmsprovider.customer.data.dto.CustomerDTO;
import com.owerp.fmsprovider.customer.data.enums.EntryType;
import com.owerp.fmsprovider.customer.data.enums.RecType;
import com.owerp.fmsprovider.customer.data.model.PersonType;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.supplier.model.dto.SupplierDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookEntryDetailsDTO {

    private Long id;
    private LedgerAccountDTO ledgerAccount;
    private Double amount;
    private EntryType entryType;
    private String details;
    private RecType recType;
    private CostCenterDTO costCenter;
    private Integer chk; // for budget creating
    @JsonIgnore
    private BookEntryDTO bookEntry;
    private CustomerDTO customer;
    private SupplierDTO supplier;
    private PersonType personType;
}
