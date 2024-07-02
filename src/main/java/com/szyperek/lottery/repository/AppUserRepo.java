package com.szyperek.lottery.repository;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import com.szyperek.lottery.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByEmail(String email);

    List<AppUser> findAllByRole(Role role);

    Optional<AppUser> findByPasswordResetToken(PasswordResetToken token);

    @Query(value = "SELECT id from app_user where email = :email", nativeQuery = true)
    Long findIdByEmail(String email);
}
