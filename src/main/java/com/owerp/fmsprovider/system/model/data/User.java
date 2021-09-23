package com.owerp.fmsprovider.system.model.data;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "sys_user", uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "username"})})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String epfNo;
    private boolean active;
}
