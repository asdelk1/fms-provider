package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.model.data.UserPermission;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserPermissionService {
    private Set<String> permissions = new HashSet<>();

    public UserPermissionService() {
        this.permissions.add(UserPermission.USER_LIST);
        this.permissions.add(UserPermission.USER_ADD);
        this.permissions.add(UserPermission.USER_EDIT);
        this.permissions.add(UserPermission.USER_DELETE);

        this.permissions.add(UserPermission.USER_GROUP_LIST);
        this.permissions.add(UserPermission.USER_GROUP_ADD);
        this.permissions.add(UserPermission.USER_GROUP_EDIT);
        this.permissions.add(UserPermission.USER_GROUP_DELETE);
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
