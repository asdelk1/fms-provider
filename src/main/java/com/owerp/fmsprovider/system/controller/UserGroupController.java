package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.system.model.data.UserGroup;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.model.dto.UserGroupDTO;
import com.owerp.fmsprovider.system.service.UserGroupService;
import com.owerp.fmsprovider.system.service.UserPermissionService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/user-groups")
public class UserGroupController {

    private final UserGroupService service;
    private final EntityModelMapper modelMapper;
    private final UserPermissionService permissionService;

    public UserGroupController(UserGroupService service, EntityModelMapper modelMapper, UserPermissionService permissionService) {
        this.service = service;
        this.modelMapper = modelMapper;
        this.permissionService = permissionService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> listUserGroups() {
        // TODO: add permission validation
        List<UserGroupDTO> list = this.service.getUserGroups().stream().map(ug -> this.modelMapper.getDTO(ug, UserGroupDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, list));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUserGroup(@PathVariable long id){
        // TODO: add permission validation
        UserGroup ug = this.service.getUserGroup(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.modelMapper.getDTO(ug, UserGroup.class)));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse>createUserGroup(@RequestBody UserGroupDTO dto){
        // TODO: add permission validation
        UserGroup ug = this.service.saveUserGroup(dto);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, ug));
    }

    @PostMapping("/{id}/add-users")
    public ResponseEntity<ApiResponse> addUsers(@PathVariable long id, @RequestBody Set<UserDTO> users){
        // TODO: add permission validation
        UserGroup ug = this.service.addUsers(id, users);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, ug));
    }

    @PostMapping("/{id}/remove-users")
    public ResponseEntity<ApiResponse> removeUsers(@PathVariable long id, @RequestBody Set<UserDTO> users){
        // TODO: add permission validation
        UserGroup ug = this.service.deleteUsers(id, users);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, ug));
    }

    @GetMapping("/permissions")
    public ResponseEntity<ApiResponse> getPermissionList() {
        Set<String> permissionList = this.permissionService.getPermissions();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, permissionList));
    }
}
