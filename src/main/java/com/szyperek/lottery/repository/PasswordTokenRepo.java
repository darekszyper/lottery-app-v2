package com.szyperek.lottery.repository;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordTokenRepo extends JpaRepository<PasswordResetToken, Long> {

    PasswordResetToken findByToken(String token);

    Optional<PasswordResetToken> findByUser(AppUser appUser);
}
