package com.owerp.fmsprovider.ledger.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_ledger_category")
@Getter
@Setter
public class LedgerCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String accCode;
    private String accName;
    private Integer comId;
    private Integer status = 1;
    @OneToOne
    private LedgerTypes ledgerType;
}
