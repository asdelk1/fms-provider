package com.owerp.fmsprovider.system.model.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UserGroupDTO {

    private Long id;
    private String name;
    private String description;
    private Set<UserDTO> users;
    private Set<String> grantedPermissions;
}
