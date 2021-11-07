package com.owerp.fmsprovider.ledger.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_ledger_type")
@Getter
@Setter
public class LedgerTypes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeName;
    private Integer typeCategory; /* 1 - Income Statements, 2 - Balance Sheet*/
    private Integer clarification;
    private Integer status;
    private String clarifi; /* D - Debit, C - Credit */

}
