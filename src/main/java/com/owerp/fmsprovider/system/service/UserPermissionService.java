package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.advice.ApplicationException;
import com.owerp.fmsprovider.system.advice.UnAuthorizedActionException;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserPermissionService {
    private final Set<String> permissions = new HashSet<>();

    private final UserService userService;

    public UserPermissionService(UserService userService) {
        this.permissions.add(UserPermission.USER_LIST);
        this.permissions.add(UserPermission.USER_ADD);
        this.permissions.add(UserPermission.USER_EDIT);
        this.permissions.add(UserPermission.USER_DELETE);

        this.permissions.add(UserPermission.USER_GROUP_LIST);
        this.permissions.add(UserPermission.USER_GROUP_ADD);
        this.permissions.add(UserPermission.USER_GROUP_EDIT);
        this.permissions.add(UserPermission.USER_GROUP_DELETE);

        this.permissions.add(UserPermission.SUPPLIER_TYPE_LIST);
        this.permissions.add(UserPermission.SUPPLIER_TYPE_ADD);
        this.permissions.add(UserPermission.SUPPLIER_TYPE_EDIT);
        this.permissions.add(UserPermission.SUPPLIER_TYPE_DELETE);


        this.userService = userService;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public boolean hasPermission(String action) throws UnAuthorizedActionException {
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        User user = this.userService.getUserByUsername(username);
        if(!user.getGrantedPermissions().contains(action)){
            throw new UnAuthorizedActionException();
        }

        return true;
    }
}
