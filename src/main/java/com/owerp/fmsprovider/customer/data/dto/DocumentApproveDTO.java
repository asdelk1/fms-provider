package com.owerp.fmsprovider.customer.data.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentApproveDTO {

    private Long invoiceId;
    private String note;
    private boolean sendEmail;

}
