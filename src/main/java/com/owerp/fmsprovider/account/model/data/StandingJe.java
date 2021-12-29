package com.owerp.fmsprovider.account.model.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.owerp.fmsprovider.customer.data.enums.BookEntryType;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.system.model.data.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fin_standing_je")
@Getter
@Setter
public class StandingJe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String entryNumber;
    private LocalDateTime entryDate;
    private Integer authorized = 2; //0-  Authorized, 1 - set for edit, 2 - Not Authorized
    @Column(columnDefinition = "varchar(750)", nullable = true)
    private String note;
    private Integer status = 1;
    @Enumerated(EnumType.STRING)
    private BookEntryType bookEntryType;

    private Integer deposited=0; // 0- undeposited, 1- deposited

    @OneToMany(
            cascade= CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "bookEntry"
    )
    /*@LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name="fin_entry_details",joinColumns={
            @JoinColumn(name="entry_id", unique=true, referencedColumnName = "id") },inverseJoinColumns={
            @JoinColumn(name="details_id" ,referencedColumnName = "id") } )*/
    private List<StandingJeDetails> bookEntryDetails = new ArrayList<>();

    private Long refId;


    //Authorization
    @Enumerated(EnumType.STRING)
    private DocApproveType docApproveType = DocApproveType.NONE;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User enteredBy;
    private LocalDateTime enteredOn;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User checkedBy;
    private LocalDateTime checkedOn;
    private String checkerNote;

    @ManyToOne
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User authorizedBy;
    @Column(nullable=true)
    private LocalDateTime authorizedOn;
    private String approverNote;
}
