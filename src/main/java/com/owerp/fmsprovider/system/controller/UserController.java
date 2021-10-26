package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserGroup;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserDTO;
import com.owerp.fmsprovider.system.model.dto.UserGroupDTO;
import com.owerp.fmsprovider.system.service.AdminService;
import com.owerp.fmsprovider.system.service.UserService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class UserController {

    private final UserService userService;
    private final AdminService adminService;
    private final EntityModelMapper modelMapper;

    public UserController(UserService userService, EntityModelMapper modelMapper, AdminService adminService) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.adminService = adminService;
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getUsers() {
        List<UserDTO> users = this.userService.getUsers();
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, users));
    }

    @PostMapping()
    public ResponseEntity<ApiResponse> save(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        UserDTO newUser = this.userService.saveUser(userDTO);
        this.adminService.generatePasswordResetToken(request, newUser.getEmail());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.CREATED, newUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getUser(@PathVariable long id) {

        User user = this.userService.getUser(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.ACCEPTED, this.modelMapper.getDTO(user, UserDTO.class)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable long id) {
        this.userService.deleteUser(id);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK));
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse> updateUser(@RequestBody UserDTO userDTO){
        UserDTO user = this.userService.saveUser(userDTO);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, user));
    }

    @PostMapping("/{id}/set-state")
    public ResponseEntity<ApiResponse> deActivateUser(@RequestBody UserDTO userDTO) {
        User user = this.userService.setUserActiveState(userDTO);
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, this.modelMapper.getDTO(user, UserDTO.class)));
    }

    @GetMapping("/{id}/user-groups")
    public ResponseEntity<ApiResponse> getUserGroups(@PathVariable long id){
        User user = this.userService.getUser(id);
        Set<UserGroupDTO> userGroups = user.getGroups().stream().map(u -> this.modelMapper.getDTO(u, UserGroupDTO.class)).collect(Collectors.toSet());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, userGroups));
    }

    @GetMapping("/{username}/permissions")
    public ResponseEntity<ApiResponse> getUserPermissions(@PathVariable String username){
        User user = this.userService.getUserByUsername(username);
        ApiResponse res = new ApiResponse(HttpStatus.OK, user.getGrantedPermissions());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username){
        User user = this.userService.getUserByUsername(username);
        ApiResponse response = new ApiResponse(HttpStatus.OK, this.modelMapper.getDTO(user, UserDTO.class));
        return ResponseEntity.ok(response);
    }
}
