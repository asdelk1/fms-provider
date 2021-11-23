package com.owerp.fmsprovider.helper.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "fin_accounting_period", uniqueConstraints = {@UniqueConstraint(columnNames = {"startDate", "endDate"})})
@Getter
@Setter
public class AccountingPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean status = true;
}
