package com.internship.juglottery.service;

import com.internship.juglottery.entity.AppUser;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    List<AppUser> getAllUsersWithUserRole();

    AppUser getUserById(Long id);

    void deleteById(Long id);

    boolean registerAccount(AppUser appUser);

    AppUser findByEmail(String email);

    Optional<AppUser> getUserByPasswordResetToken(String token);

    Long getUserIdByEmail(String email);
}
