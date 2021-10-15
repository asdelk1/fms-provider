package com.owerp.fmsprovider.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserLoginHistoryDTO {

    private Long id;
    private LocalDateTime loggedInTime;
    private LocalDateTime loggedOutTime;
    private String ip;
    private String host;
    private UserDTO user;
}
