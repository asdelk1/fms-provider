package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.system.model.data.UserLoginHistory;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserLoginHistoryDTO;
import com.owerp.fmsprovider.system.service.UserLoginService;
import com.owerp.fmsprovider.system.service.UserPermissionService;
import com.owerp.fmsprovider.system.util.EntityModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user-login-history")
public class UserLoginHistoryController {

    private final UserLoginService service;
    private final UserPermissionService permissionService;
    private final EntityModelMapper modelMapper;

    public UserLoginHistoryController(UserLoginService service, UserPermissionService permissionService, EntityModelMapper modelMapper) {
        this.service = service;
        this.permissionService = permissionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> list(){
        List<UserLoginHistory> history = this.service.getAll();
        List<UserLoginHistoryDTO> dtoList = history.stream().map(h -> this.modelMapper.getDTO(h, UserLoginHistoryDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(new ApiResponse(HttpStatus.OK, dtoList));
    }
}
