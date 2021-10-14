package com.owerp.fmsprovider.system.model.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "sys_user_group", uniqueConstraints = @UniqueConstraint(columnNames = {"name"}))
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany()
    @JoinTable(name = "sys_user_group_mapping",
            joinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    )
    private Set<User> users;
    @ElementCollection
    private Set<String> grantedPermissions;

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name);
    }
}
