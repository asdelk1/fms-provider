package com.owerp.fmsprovider.helper.model.data;

import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_tax_type", uniqueConstraints = {@UniqueConstraint(columnNames = {"taxCode"})})
@Getter
@Setter
public class FinTaxType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String taxCode;
    private Double taxRate;
    private String description;
    private Boolean status = true;
    @OneToOne
    private LedgerAccount controlAccount;
}
