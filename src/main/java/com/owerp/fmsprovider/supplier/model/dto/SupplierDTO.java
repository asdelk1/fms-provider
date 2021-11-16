package com.owerp.fmsprovider.supplier.model.dto;

import com.owerp.fmsprovider.ledger.model.dto.LedgerAccountDTO;
import com.owerp.fmsprovider.payment.model.dto.PaymentMethodDTO;
import com.owerp.fmsprovider.payment.model.dto.PaymentTermsDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierDTO {
    private Long id;
    private String code;
    private String name;
    private SupplierTypeDTO type;
    private String address;
    private String contactPerson;
    private String printOnCheque;
    private String city;
    private Boolean status = true;

    private String phoneNo;
    private String mobileNo;
    private String fax;
    private String email;
    private String webSite;

    private String vatNo;
    private String sVatNo;
    private String businessRegNo;

    private String accountNo;
    private Double creditLimit;
    private PaymentTermsDTO paymentTerms;
    private PaymentMethodDTO paymentMethod;
    private LedgerAccountDTO controlAccount;
}
