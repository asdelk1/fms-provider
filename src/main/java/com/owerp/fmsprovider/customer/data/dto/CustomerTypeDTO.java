package com.owerp.fmsprovider.customer.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerTypeDTO {

    private Long id;
    private String typeCode;
    private String typeName;
    private String remarks;
    private Boolean status = true;
}
