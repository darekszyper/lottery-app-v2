package com.internship.juglottery.repository;

import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    Optional<PasswordResetToken> findByUser(AppUser appUser);
}
