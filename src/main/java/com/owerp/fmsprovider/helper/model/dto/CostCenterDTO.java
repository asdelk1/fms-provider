package com.owerp.fmsprovider.helper.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CostCenterDTO {

    private Long id;
    private String name;
    private String code;
    private String remarks;
    private Boolean status = true;
}
