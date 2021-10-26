package com.owerp.fmsprovider.system.model.data;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "sys_password_reset_token")
@Getter
public class PasswordResetToken {

    @Id
    private String token;
    private LocalDateTime createdOn;
    @OneToOne
    private User user;

    public PasswordResetToken() {
    }

    public PasswordResetToken(User user) {
        this.token = UUID.randomUUID().toString();
        this.createdOn = LocalDateTime.now();
        this.user = user;
    }
}
