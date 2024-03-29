package com.owerp.fmsprovider.helper.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_payment_terms", uniqueConstraints = {@UniqueConstraint(columnNames = {"term"})})
@Getter
@Setter
public class PaymentTerms {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer term;
    private String description;
    private Double discount;
    private Integer discountDatesBefore;
    private Boolean status = true;
}
