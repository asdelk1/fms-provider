package com.owerp.fmsprovider.investment.model.data;

import com.owerp.fmsprovider.investment.model.enums.InvestmentTimeUnit;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fin_investment_type")
@Getter
@Setter
public class InvestmentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private boolean active;
    @Enumerated(EnumType.STRING)
    private InvestmentTimeUnit timeUnit;
    private Integer firstReminder;
    @Enumerated(EnumType.STRING)
    private InvestmentTimeUnit firstReminderTimeUnit;
    private Integer secondReminder;
    @Enumerated(EnumType.STRING)
    private InvestmentTimeUnit secondReminderTimeUnit;
    private Integer thirdReminder;
    @Enumerated(EnumType.STRING)
    private InvestmentTimeUnit thirdReminderTimeUnit;
}
