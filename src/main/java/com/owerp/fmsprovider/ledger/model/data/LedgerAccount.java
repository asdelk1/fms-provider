package com.owerp.fmsprovider.ledger.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_ledger_account")
@Getter
@Setter
public class LedgerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ledgerAccCode;
    private String ledgerAccName;
    private Boolean status = true;
    @ManyToOne
    private LedgerCategory ledgerCategory;
}
