package com.owerp.fmsprovider.customer.data.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_customer_item_formula")
@Getter
@Setter
public class CustomerItemFormula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double fromAmount;
    private Double toAmount;
    private Double amount;
    private Double percentage;
    private Double graterThanAmount;
    private Double perUnitAmount;
    private Double perUnitValue;
    private Boolean greaterThan = false;
}
