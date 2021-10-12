package com.owerp.fmsprovider.system.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String epfNo;
    private String email;
    private boolean active;
    private Set<String> grantedPermissions;
}
