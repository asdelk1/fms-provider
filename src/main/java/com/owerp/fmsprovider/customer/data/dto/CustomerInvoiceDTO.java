package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.account.model.dto.BookEntryDTO;
import com.owerp.fmsprovider.customer.data.enums.InvoicePaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerInvoiceDTO {

    private Long id;
    private CustomerDTO customer;
    private String invoiceNumber;
    private double toBePaid;
    private int creditPeriod;
    private BookEntryDTO bookEntry;
    private double amount;
    private InvoicePaymentStatus invoicePaymentStatus = InvoicePaymentStatus.NONE;
    private LocalDate invoiceDate;
}
