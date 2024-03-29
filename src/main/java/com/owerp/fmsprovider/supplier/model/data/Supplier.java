package com.owerp.fmsprovider.supplier.model.data;

import com.owerp.fmsprovider.helper.model.data.PaymentMethod;
import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_supplier")
@Getter
@Setter
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    @OneToOne
    private SupplierType type;
    private String address;
    private String contactPerson;
    private String printOnCheque;
    private String city;
    private String phoneNo;
    private String mobileNo;
    private String fax;
    private String email;
    private String webSite;
    private String vatNo;
    private String sVatNo;
    private String businessRegNo;
    private String accountNo;
    @OneToOne
    private PaymentTerms paymentTerms;
    @OneToOne
    private PaymentMethod paymentMethod;
    private Double creditLimit;
    @OneToOne
    private LedgerAccount controlAccount;
    private Boolean status;
}
