package com.owerp.fmsprovider.system.model.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
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
    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private Set<UserGroup> groups;

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.username);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || obj.getClass() != this.getClass()){
            return false;
        }

        User u = (User) obj;
        return u.id.equals(this.id);
    }
}
