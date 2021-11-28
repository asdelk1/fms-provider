package com.owerp.fmsprovider.helper.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_currency_type", uniqueConstraints = {@UniqueConstraint(name = "UniqueCurrency", columnNames = {"currency"})})
@Getter
@Setter
public class CurrencyType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String currency;
    private Boolean status = true;
}
