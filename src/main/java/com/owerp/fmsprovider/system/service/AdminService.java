package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.config.security.TokenProvider;
import com.owerp.fmsprovider.system.advice.ApplicationException;
import com.owerp.fmsprovider.system.advice.EntityNotFoundException;
import com.owerp.fmsprovider.system.model.data.PasswordResetToken;
import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserLoginHistory;
import com.owerp.fmsprovider.system.model.dto.PasswordResetRequest;
import com.owerp.fmsprovider.system.model.dto.UserCredential;
import com.owerp.fmsprovider.system.repository.PasswordResetTokenRepository;
import com.owerp.fmsprovider.system.repository.UserLoginHistoryRepo;
import com.owerp.fmsprovider.system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AdminService {

    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserLoginHistoryRepo historyRepo;
    private final UserService userService;
    private final PasswordResetTokenRepository tokenRepository;

    private final UserRepository userRepository;
    private final EmailService emailService;

    private final Map<Integer, UserLoginHistory> loggedInUsers = new HashMap<>();
    @Value("${owerp.client.port}")
    private String clientPort;

    public AdminService(TokenProvider tokenProvider, AuthenticationManager authenticationManager, UserLoginHistoryRepo historyRepo, UserService userService, PasswordResetTokenRepository tokenRepository, UserRepository userRepository, EmailService emailService) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.historyRepo = historyRepo;
        this.userService = userService;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public String generateToken(UserCredential credential, String ipAddress, String remoteHost) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(credential.getEmail(), credential.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = this.tokenProvider.generateToken(authentication);

        UserDetails user = (UserDetails) authentication.getPrincipal();
        this.addLoginHistory(user.getUsername(), ipAddress, remoteHost);

        return token;
    }

    public void addLoginHistory(String username, String ip, String host) {
        User user = this.userService.getUserByUsername(username);
        UserLoginHistory history = new UserLoginHistory(user, ip, host);
        history = this.historyRepo.save(history);
        int hash = this.getKey(ip);
        this.loggedInUsers.put(hash, history);
    }

    public void completeUserSession(String ip) {

        int hash = this.getKey(ip);
        UserLoginHistory history = this.loggedInUsers.get(hash);
        if (history == null) {
            return;
            // throw an exception if needed
        }

        history.setLoggedOutTime(LocalDateTime.now());
        this.historyRepo.save(history);
    }

    public List<UserLoginHistory> getAll() {
        return this.historyRepo.findAll();
    }

    private int getKey(String ip) {
        return Objects.hash(ip);
    }

    public PasswordResetToken generatePasswordResetToken(HttpServletRequest request, String email) {

        Optional<User> user = this.userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User", email);
        }
        PasswordResetToken token = new PasswordResetToken(user.get());
        token = this.tokenRepository.save(token);

        // TODO: here need to send the email with password reset link
        String link = this.generatePasswordResetLink(request, token);
        Map<String, Object> context = new HashMap<>();
        context.put("resetURL", link);
        context.put("firstName", user.get().getFirstName());
        context.put("lastName", user.get().getLastName());
        this.emailService.sendEmail("reset-password", context, email, "Password Reset Token");
        return token;
    }

    public void resetPassword(PasswordResetRequest request) throws ApplicationException {
        PasswordResetToken token = this.tokenRepository.getById(request.getToken());
        if (token == null) {
            throw new EntityNotFoundException("Token", request.getToken());
        }

        if (this.isTokenExpired(token)) {
            this.tokenRepository.delete(token);
            throw new ApplicationException("Password Reset Token had been expired");
        }
        this.userService.updatePassword(token.getUser(), request.getPassword());
    }

    private boolean isTokenExpired(PasswordResetToken token) {

        LocalDateTime createdTime = token.getCreatedOn();
        long hoursDiff = ChronoUnit.HOURS.between(createdTime, LocalDateTime.now());
        return hoursDiff > 24;
    }

    private String generatePasswordResetLink(HttpServletRequest request, PasswordResetToken token) {
        return "http://" + request.getServerName() + ":" + this.clientPort + request.getContextPath() + "/auth/reset-password" +
                "?token=" +
                token.getToken();
    }
}
