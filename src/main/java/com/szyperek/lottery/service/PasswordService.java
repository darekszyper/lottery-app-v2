package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.AppUser;

public interface PasswordService {
    String generatePassword();

    void createPasswordResetTokenForUser(AppUser appUser, String token);

    void validatePasswordResetToken(String token);

    void changeUserPassword(AppUser appUser, String newPassword);
}
