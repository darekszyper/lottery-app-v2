package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import com.szyperek.lottery.exception.InvalidTokenException;
import com.szyperek.lottery.repository.PasswordTokenRepo;
import com.szyperek.lottery.service.AppUserService;
import com.szyperek.lottery.service.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {
    public static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    public static final String DIGITS = "0123456789";
    public static final String SPECIAL_CHARS = "@$!%*#?&";
    private final PasswordTokenRepo passwordTokenRepo;
    private final PasswordEncoder passwordEncoder;
    private final AppUserService appUserService;

    @Override
    public String generatePassword() {
        SecureRandom random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        password.append(UPPER.charAt(random.nextInt(UPPER.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        password.append(SPECIAL_CHARS.charAt(random.nextInt(SPECIAL_CHARS.length())));

        int remainingLength = 7;
        String allCharacters = UPPER + LOWER + DIGITS + SPECIAL_CHARS;
        for (int i = 0; i < remainingLength; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        return shuffleString(password.toString());
    }

    private String shuffleString(String input) {
        char[] charArray = input.toCharArray();
        SecureRandom random = new SecureRandom();
        for (int i = charArray.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            char temp = charArray[index];
            charArray[index] = charArray[i];
            charArray[i] = temp;
        }
        return new String(charArray);
    }

    @Transactional
    @Override
    public void createPasswordResetTokenForUser(AppUser appUser, String token) {
        Optional<PasswordResetToken> oldToken = passwordTokenRepo.findByUser(appUser);
        oldToken.ifPresent(passwordTokenRepo::delete);

        PasswordResetToken myToken = new PasswordResetToken();
        myToken.setToken(token);
        myToken.setUser(appUser);
        myToken.setExpiryDate(LocalDateTime.now().plusHours(24));

        passwordTokenRepo.save(myToken);
    }

    @Override
    public void validatePasswordResetToken(String token) {
        final PasswordResetToken passToken = passwordTokenRepo.findByToken(token);

        if (!isTokenFound(passToken) || isTokenExpired(passToken))
            throw new InvalidTokenException("Invalid change password token");

    }

    private boolean isTokenFound(PasswordResetToken passToken) {
        return passToken != null;
    }

    private boolean isTokenExpired(PasswordResetToken passToken) {
        final LocalDateTime now = LocalDateTime.now();
        return passToken.getExpiryDate().isBefore(now);
    }

    @Override
    public void changeUserPassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserService.registerAccount(appUser);
    }
}

