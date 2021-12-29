package com.owerp.fmsprovider.account.model.data;

import com.owerp.fmsprovider.customer.data.enums.EntryType;
import com.owerp.fmsprovider.customer.data.enums.RecType;
import com.owerp.fmsprovider.customer.data.model.Customer;
import com.owerp.fmsprovider.customer.data.model.PersonType;
import com.owerp.fmsprovider.helper.model.data.CostCenter;
import com.owerp.fmsprovider.ledger.model.data.LedgerAccount;
import com.owerp.fmsprovider.supplier.model.data.Supplier;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_standing_je_details")
@Getter
@Setter
public class StandingJeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private LedgerAccount ledgerAccount;
    private Double amount;
    @Enumerated(EnumType.STRING)
    private EntryType entryType;
    @Column(columnDefinition = "varchar(750)", nullable = true)
    private String details;
    @Enumerated(EnumType.STRING)
    private RecType recType = RecType.N;
    @OneToOne
    CostCenter costCenter;
    private Integer chk = 0; // for budget creating

    @ManyToOne
    /*@JoinTable(name="fin_entry_details",joinColumns={
            @JoinColumn(name="details_id" ,referencedColumnName = "id") },inverseJoinColumns={
            @JoinColumn(name="entry_id",referencedColumnName = "id") } )*/
    private StandingJe bookEntry;
    @OneToOne(optional = true)
    private Customer customer;
    @OneToOne(optional = true)
    private Supplier supplier;
    @Enumerated(EnumType.STRING)
    private PersonType personType=PersonType.NONE;
}
