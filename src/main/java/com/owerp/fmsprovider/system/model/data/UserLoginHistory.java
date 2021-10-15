package com.owerp.fmsprovider.system.model.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class UserLoginHistory {

    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime loggedInTime;
    private LocalDateTime loggedOutTime;
    private String ip;
    private String host;
    @OneToOne
    private User user;

    public UserLoginHistory(){
    }

    public UserLoginHistory(User user, String ip){
        this.loggedInTime = LocalDateTime.now();
        this.ip = ip;
        this.user = user;
    }
}
