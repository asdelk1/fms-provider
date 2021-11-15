package com.owerp.fmsprovider.supplier.model.data;

import com.owerp.fmsprovider.supplier.model.enums.NumTypes;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "fin_numbers")
public class FinNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private NumTypes numType;
    private Long number;
}
