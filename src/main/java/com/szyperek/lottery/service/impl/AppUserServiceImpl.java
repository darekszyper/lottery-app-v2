package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import com.szyperek.lottery.exception.UniqueUserEmailException;
import com.szyperek.lottery.repository.AppUserRepo;
import com.szyperek.lottery.repository.PasswordTokenRepo;
import com.szyperek.lottery.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.szyperek.lottery.entity.enums.Role.USER;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepo appUserRepo;
    private final PasswordTokenRepo passwordTokenRepo;

    @Override
    public List<AppUser> getAllUsersWithUserRole() {
        return appUserRepo.findAllByRole(USER);
    }

    @Override
    public AppUser getUserById(Long id) {
        return appUserRepo.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        if (appUserRepo.existsById(id)) {
            appUserRepo.deleteById(id);
        }
    }

    @Override
    public boolean registerAccount(AppUser appUser) {
        try {
            appUserRepo.save(appUser);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueUserEmailException("Email already exists in DB");
        }
        return true;
    }

    @Override
    public AppUser findByEmail(String email) {
        return appUserRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " doesn't exist"));
    }

    @Transactional
    @Override
    public Optional<AppUser> getUserByPasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordTokenRepo.findByToken(token);
        return appUserRepo.findByPasswordResetToken(passwordResetToken);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return appUserRepo.findIdByEmail(email);
    }
}
