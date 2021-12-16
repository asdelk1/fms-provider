package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.customer.data.enums.InvoiceType;
import com.owerp.fmsprovider.helper.model.dto.PaymentMethodDTO;
import com.owerp.fmsprovider.helper.model.dto.PaymentTermsDTO;
import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CustomerDTO {

    private Long id;
    private CustomerTypeDTO customerType;
    private String customerCode;
    private String customerName;
    private String nameOnInvoice;
    private String address;
    private LocalDate registerDate;
    private Boolean status = true;

    private String cpName1;
    private String cpPhoneNo1;
    private String cpMobileNo1;
    private String cpEmailNo1;

    private String cpName2;
    private String cpPhoneNo2;
    private String cpMobileNo2;
    private String cpEmailNo2;

    private String vatNo;
    private String svatNo;
    private String businessRegNo;
    private InvoiceType invoiceType;

    private PaymentTermsDTO paymentTerms;
    private PaymentMethodDTO paymentMethod;
    private LedgerAccountDTO controlAccount;
}
