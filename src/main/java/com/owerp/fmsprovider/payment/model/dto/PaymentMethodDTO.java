package com.owerp.fmsprovider.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentMethodDTO {

    private Long id;
    private String paymentMethod;
    private String description;
    private Boolean status;
}
