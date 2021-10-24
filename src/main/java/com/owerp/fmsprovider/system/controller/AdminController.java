package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.system.model.data.PasswordResetToken;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserCredential;
import com.owerp.fmsprovider.system.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody UserCredential credential, HttpServletRequest request) {

        final String token = this.adminService.generateToken(credential, request.getRemoteAddr(), request.getRemoteHost());
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, "", token);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/generate-password-reset-token")
    public ResponseEntity<ApiResponse> generatePasswordResetToken(@RequestBody String username){
        final PasswordResetToken tokenObj = this.adminService.generatePasswordResetToken(username);
        ApiResponse res = new ApiResponse(HttpStatus.OK, tokenObj.getToken());
        return ResponseEntity.ok(res);
    }


}
