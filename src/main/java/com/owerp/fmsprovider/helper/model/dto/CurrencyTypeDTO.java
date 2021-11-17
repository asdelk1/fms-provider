package com.owerp.fmsprovider.helper.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyTypeDTO {
    private Long id;
    private String currency;
    private Boolean status = true;
}
