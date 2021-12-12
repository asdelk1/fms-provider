package com.owerp.fmsprovider.customer.data.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class
SalesInvoiceDetailDTO {

    private List<InvoiceTaxDetailsDTO> taxList;
    private SalesInvoiceItemDTO salesInvoiceItem;
    private CreditNoteItemDTO creditNoteItem;
    private List<CreditNoteTaxDetailsDTO> cnTaxList;
}
