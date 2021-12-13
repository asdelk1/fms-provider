package com.owerp.fmsprovider.customer.data.dto;

import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.enums.InvoiceType;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.helper.model.dto.PaymentTermsDTO;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SalesInvoiceDTO {

    private Long id;
    private CustomerDTO customer;
    private String invoiceNumber;
    private InvoiceType invoiceType;
    private String invoiceAddress;
    private String memo;
    private LocalDate invoiceDate;
    private CostCenterDTO costCenter;
    private String poNumber;
    private PaymentTermsDTO paymentTerms;
    private String message;
    private List<SalesInvoiceItemDTO> salesInvoiceItems= new ArrayList<>();

    //Payments
    private Double taxTotalAmount;
    private Double totalAmount;
    private List<InvoiceTaxDetailsDTO> invoiceTaxDetails;

    //Authorization
    private DocApproveType docApproveType = DocApproveType.NONE;


    private UserDTO enteredBy;
    private LocalDateTime enteredOn;

    private UserDTO checkedBy;
    private LocalDateTime checkedOn;
    private String checkerNote;

    private UserDTO authorizedBy;
    private LocalDateTime authorizedOn;
    private String approverNote;

    private Boolean emailSent;
    private LocalDateTime emailSentOn;
    private String paymentStatus;
}
