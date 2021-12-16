package com.owerp.fmsprovider.customer.data.model;


import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.helper.model.data.TaxGroup;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "fin_credit_note_items")
public class CreditNoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    CustomerItem customerItem;
    String itemDescription;
    Double unitValue;
    Double amount;
    @ManyToOne
    TaxGroup taxGroup;
    @ManyToOne
    CostCenter costCenter;
    private Double taxAmount;
}
