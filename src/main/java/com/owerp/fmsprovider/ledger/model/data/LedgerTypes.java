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
    private Boolean clarification;
    private Integer status;
    private String clarifi; /* D - Debit, C - Credit */

    public LedgerTypes() {}

    public LedgerTypes(Boolean clarification,Integer status, Integer typeCategory, String typeName) {
        this.typeName = typeName;
        this.typeCategory = typeCategory;
        this.clarification = clarification;
        this.status = status;
    }
}
