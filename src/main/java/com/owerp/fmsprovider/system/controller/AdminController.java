package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.config.security.TokenProvider;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.dto.ApiResponse;
import com.owerp.fmsprovider.system.model.dto.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/token")
public class AdminController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

    public AdminController(AuthenticationManager authenticationManager, TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/generate-token")
    public ResponseEntity<ApiResponse> generateToken(@RequestBody UserCredential credential){

        Authentication authToken = new UsernamePasswordAuthenticationToken(credential.getUsername(), credential.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = this.tokenProvider.generateToken(authentication);
        ApiResponse apiResponse = new ApiResponse(HttpStatus.OK, "", token);
        return ResponseEntity.ok(apiResponse);
    }


}
