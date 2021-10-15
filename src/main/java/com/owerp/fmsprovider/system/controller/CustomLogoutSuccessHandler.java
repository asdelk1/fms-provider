package com.owerp.fmsprovider.system.controller;

import com.owerp.fmsprovider.system.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomLogoutSuccessHandler extends HttpStatusReturningLogoutSuccessHandler implements LogoutSuccessHandler  {

    @Autowired
    private UserLoginService service;

    public CustomLogoutSuccessHandler() {
        super(HttpStatus.OK);
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

//        String username = ((UserDetails) authentication).getUsername();
        String username = "";
        String ip = request.getRemoteAddr();
        service.completeUserSession(username, ip);

        super.onLogoutSuccess(request, response, authentication);
    }
}
