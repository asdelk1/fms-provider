package com.owerp.fmsprovider.payment.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentTermsDTO {
    private Long id;
    private Integer term;
    private String description;
    private Double discount;
    private Integer discountDatesBefore;
    private Boolean status = true;
}
