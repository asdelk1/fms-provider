package com.owerp.fmsprovider.customer.data.model;


import com.owerp.fmsprovider.helper.model.data.FinTaxType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "fin_sales_invoice_tax")
public class InvoiceTaxDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private FinTaxType finTaxType;
    private Double taxAmount;
}
