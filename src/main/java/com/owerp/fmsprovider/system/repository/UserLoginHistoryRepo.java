package com.owerp.fmsprovider.system.repository;

import com.owerp.fmsprovider.system.model.data.UserLoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserLoginHistoryRepo extends JpaRepository<UserLoginHistory, Long> {




}
