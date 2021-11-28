package com.owerp.fmsprovider.helper.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {
    private Long id;
    private String paymentMethod;
    private String description;
    private Boolean status = true;
}
