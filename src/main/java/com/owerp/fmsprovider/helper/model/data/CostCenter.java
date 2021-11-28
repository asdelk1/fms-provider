package com.owerp.fmsprovider.helper.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "fin_cost_center", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "code"})})
public class CostCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    private String remarks;
    private Boolean status = true;
}
