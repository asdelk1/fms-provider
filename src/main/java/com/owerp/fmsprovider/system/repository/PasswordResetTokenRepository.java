package com.owerp.fmsprovider.system.repository;

import com.owerp.fmsprovider.system.model.data.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String> {
}
