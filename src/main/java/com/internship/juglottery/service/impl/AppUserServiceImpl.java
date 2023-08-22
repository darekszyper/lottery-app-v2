package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.PasswordResetToken;
import com.internship.juglottery.exception.UniqueUserEmailException;
import com.internship.juglottery.repository.AppUserRepo;
import com.internship.juglottery.repository.PasswordTokenRepo;
import com.internship.juglottery.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.internship.juglottery.entity.enums.Role.USER;

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
