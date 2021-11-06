package com.owerp.fmsprovider.supplier.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SupplierItemDTO {

    private Long id;
    private String name;
    private String description;
    private boolean state;
}
