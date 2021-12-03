package com.owerp.fmsprovider.customer.data.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.customer.data.enums.InvoiceType;
import com.owerp.fmsprovider.helper.model.dto.CostCenterDTO;
import com.owerp.fmsprovider.helper.model.dto.PaymentTermsDTO;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate invoiceDate;
    //    @NotNull(message = "Select cost center")
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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime enteredOn;

    private UserDTO checkedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkedOn;
    private String checkerNote;

    private UserDTO authorizedBy;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime authorizedOn;
    private String approverNote;

    private Boolean emailSent;
    private LocalDateTime emailSentOn;
    private String paymentStatus;
}
