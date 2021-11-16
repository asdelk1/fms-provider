package com.owerp.fmsprovider.supplier.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "supplier_type")
public class SupplierType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String typeCode;
    private String typeName;
    private String remarks;
    private Boolean status = true;
}
