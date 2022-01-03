package com.owerp.fmsprovider.investment.model.dto;

import com.owerp.fmsprovider.investment.model.enums.InvestmentTimeUnit;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvestmentTypeDTO {

    private Long id;
    private String code;
    private String name;
    private boolean active;
    private InvestmentTimeUnit timeUnit;
    private Integer firstReminder;
    private InvestmentTimeUnit firstReminderTimeUnit;
    private Integer secondReminder;
    private InvestmentTimeUnit secondReminderTimeUnit;
    private Integer thirdReminder;
    private InvestmentTimeUnit thirdReminderTimeUnit;
}
