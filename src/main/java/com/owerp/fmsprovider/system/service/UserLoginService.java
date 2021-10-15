package com.owerp.fmsprovider.system.service;

import com.owerp.fmsprovider.system.model.data.User;
import com.owerp.fmsprovider.system.model.data.UserLoginHistory;
import com.owerp.fmsprovider.system.repository.UserLoginHistoryRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLoginService {

    private final UserLoginHistoryRepo repo;
    private final UserService userService;
    private Map<String, UserLoginHistory> loggedInUsers = new HashMap<>();

    public UserLoginService(UserLoginHistoryRepo repo, UserService userService) {
        this.repo = repo;
        this.userService = userService;
    }

    public void addLoginHistory(String username, String ip, String host) {
        User user = this.userService.getUserByUsername(username);
        UserLoginHistory history = new UserLoginHistory(user, ip);
        history = this.repo.save(history);
        this.loggedInUsers.put(username, history);
    }

    public List<UserLoginHistory> getAll(){
        return this.repo.findAll();
    }
}
