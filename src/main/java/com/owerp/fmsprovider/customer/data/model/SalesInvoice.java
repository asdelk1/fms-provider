package com.owerp.fmsprovider.customer.data.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.enums.InvoiceType;
import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.data.PaymentTerms;
import com.owerp.fmsprovider.system.model.data.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fin_sales_invoice")
@Getter
@Setter
public class SalesInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    private String invoiceNumber;
    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;
    private String invoiceAddress;
    @Column(columnDefinition = "varchar(750)", nullable = true)
    private String memo;
    private LocalDate invoiceDate;
    @ManyToOne
    private CostCenter costCenter;
    private String poNumber;
    @ManyToOne
    private PaymentTerms paymentTerms;
    @Column(columnDefinition = "varchar(750)", nullable = true)
    private String message;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "salesInvoice_id")
    private List<SalesInvoiceItem> salesInvoiceItems = new ArrayList<>();


    //Payments
    private Double taxTotalAmount;
    private Double totalAmount;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JoinColumn(name = "salesInvoice_id")
    private List<InvoiceTaxDetails> invoiceTaxDetails = new ArrayList<>();

    //Authorization
    @Enumerated(EnumType.STRING)
    private DocApproveType docApproveType = DocApproveType.NONE;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User enteredBy;
    private LocalDateTime enteredOn;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User checkedBy;
    private LocalDateTime checkedOn;
    private String checkerNote;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User authorizedBy;
    private LocalDateTime authorizedOn;
    private String approverNote;

    //Email
    private Boolean emailSent = false;
    private LocalDateTime emailSentOn;
}
