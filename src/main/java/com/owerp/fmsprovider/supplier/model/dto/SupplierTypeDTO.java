package com.owerp.fmsprovider.supplier.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierTypeDTO {

    private Long id;
    private String typeCode;
    private String typeName;
    private String remarks;
    private Boolean status = true;
}
