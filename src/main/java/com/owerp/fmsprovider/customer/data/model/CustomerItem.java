package com.owerp.fmsprovider.customer.data.model;

import com.owerp.fmsprovider.customer.data.enums.CustomerFormulaType;
import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "fin_customer_item")
@Getter
@Setter
public class CustomerItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private CustomerType customerType;
    private String itemName;
    private String remarks;
    private Boolean status = true;
    @OneToOne
    private LedgerAccount ledgerAccount;
    @Enumerated(EnumType.STRING)
    private CustomerFormulaType formulaType;
    private Double fixedAmount;
    private Double fixedPercentage;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("fromAmount ASC")
    private Set<CustomerItemFormula> formulaSlab = new HashSet<>();
}
