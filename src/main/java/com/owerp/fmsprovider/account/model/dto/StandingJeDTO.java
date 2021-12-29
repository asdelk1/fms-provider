package com.owerp.fmsprovider.account.model.dto;

import com.owerp.fmsprovider.customer.data.enums.BookEntryType;
import com.owerp.fmsprovider.customer.data.enums.DocApproveType;
import com.owerp.fmsprovider.system.model.data.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StandingJeDTO {
    private Long id;
    private String entryNumber;
    private LocalDateTime entryDate;
    private Integer authorized = 0; //0- Not Authorized, 1 - set for edit, 2 - Authorized
    private String note;
    private Integer status = 1;
    private BookEntryType bookEntryType;

    private Integer deposited=0; // 0- undeposited, 1- deposited
    private List<StandingJeDetailsDTO> bookEntryDetails;
    private Long refId;

    private DocApproveType docApproveType = DocApproveType.NONE;
    private User enteredBy;
    private LocalDateTime enteredOn;

    private User checkedBy;
    private LocalDateTime checkedOn;
    private String checkerNote;

    private User authorizedBy;
    private LocalDateTime authorizedOn;
    private String approverNote;

}
