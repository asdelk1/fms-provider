package com.owerp.fmsprovider.customer.data.model;

import com.owerp.fmsprovider.customer.data.enums.InvoiceType;
import com.owerp.fmsprovider.helper.model.data.PaymentMethod;
import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fin_customer")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CustomerType customerType;
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
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @OneToOne
    private PaymentTerms paymentTerms;
    @OneToOne
    private PaymentMethod paymentMethod;
    @OneToOne
    private LedgerAccount controlAccount;
}
