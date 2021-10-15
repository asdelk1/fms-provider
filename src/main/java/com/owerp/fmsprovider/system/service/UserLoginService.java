package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserLoginHistory;
import com.owerp.fmsprovider.system.repository.UserLoginHistoryRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class UserLoginService {

    private final UserLoginHistoryRepo repo;
    private final UserService userService;
    private Map<Integer, UserLoginHistory> loggedInUsers = new HashMap<>();

    public UserLoginService(UserLoginHistoryRepo repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    public void addLoginHistory(String username, String ip, String host) {
        User user = this.userService.getUserByUsername(username);
        UserLoginHistory history = new UserLoginHistory(user, ip);
        history = this.repo.save(history);
        int hash = this.getKey(username, ip);
        this.loggedInUsers.put(hash, history);
    }

    public void completeUserSession(String username, String ip) {

        int hash = this.getKey(username, ip);
        UserLoginHistory history = this.loggedInUsers.get(hash);
        if (history == null) {
            return;
            // throw an exception if needed
        }

        history.setLoggedOutTime(LocalDateTime.now());
        this.repo.save(history);
    }

    public List<UserLoginHistory> getAll() {
        return this.repo.findAll();
    }

    private int getKey(String username, String ip) {
//        return Objects.hash(username, ip);
        return Objects.hash(ip);
    }
}
