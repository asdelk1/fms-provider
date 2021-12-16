package com.owerp.fmsprovider.customer.data.model;

import com.owerp.fmsprovider.customer.data.enums.InvoicePaymentStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "fin_customer_invoice")
public class CustomerInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Customer customer;
    private String invoiceNumber;
    private double toBePaid;
    private int creditPeriod;
    @ManyToOne
    private BookEntry bookEntry;
    private double amount;
    @Enumerated(EnumType.STRING)
    private InvoicePaymentStatus invoicePaymentStatus = InvoicePaymentStatus.NONE;
    private LocalDate invoiceDate;
}
