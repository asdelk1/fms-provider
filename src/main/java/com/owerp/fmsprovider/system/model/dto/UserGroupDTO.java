package com.owerp.fmsprovider.system.model.dto;

import com.owerp.fmsprovider.system.model.data.User;
import lombok.Data;

import java.util.Set;

@Data
public class UserGroupDTO {

    private Long id;
    private String name;
    private String description;
    private Set<UserDTO> users;
}
