package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.config.security.TokenProvider;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserCredential;
import com.owerp.fmsprovider.system.service.UserLoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/token")
public class AdminController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserLoginService loginService;

    public AdminController(AuthenticationManager authenticationManager, TokenProvider tokenProvider, UserLoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
        this.loginService = loginService;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody UserCredential credential, HttpServletRequest request){

        Authentication authToken = new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = this.tokenProvider.generateToken(authentication);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, "", token);

        UserDetails user = (UserDetails) authentication.getPrincipal();

        this.loginService.addLoginHistory(user.getUsername(), request.getRemoteAddr(), request.getRemoteHost());
        return ResponseEntity.ok(apiResponse);
    }



}
