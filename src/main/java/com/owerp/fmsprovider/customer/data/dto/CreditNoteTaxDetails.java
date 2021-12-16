package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.helper.model.data.FinTaxType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_credit_note_tax")
@Getter
@Setter
public class CreditNoteTaxDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private FinTaxType finTaxType;
    private Double taxAmount;
}
