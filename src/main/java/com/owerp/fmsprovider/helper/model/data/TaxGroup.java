package com.owerp.fmsprovider.helper.model.data;

import com.owerp.fmsprovider.helper.model.enums.TaxOperator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_tax_group", uniqueConstraints = {@UniqueConstraint(columnNames = {"groupCode"})})
@Getter
@Setter
public class TaxGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String groupCode;
    private String description;
    private Boolean status = true;
    @Enumerated(EnumType.STRING)
    private TaxOperator taxOperator_01;
    @OneToOne
    private FinTaxType finTaxType_01;
    @OneToOne
    private FinTaxType finTaxType_02;

}
