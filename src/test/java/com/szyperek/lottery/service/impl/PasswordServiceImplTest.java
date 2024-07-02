package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import com.szyperek.lottery.exception.InvalidTokenException;
import com.szyperek.lottery.repository.PasswordTokenRepo;
import com.szyperek.lottery.service.AppUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordServiceImplTest {

    @Mock
    PasswordTokenRepo passwordTokenRepo;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AppUserService appUserService;

    @Mock
    AppUser appUser;

    @Mock
    PasswordResetToken passwordResetToken;

    @InjectMocks
    PasswordServiceImpl passwordServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should generate password with valid chars")
    void shouldGeneratePassword() {
        //given
        String regex = "^[A-Za-z0-9 #?!@$%^&*-]+$";

        //when
        String result = passwordServiceImpl.generatePassword();

        //then
        assertEquals(10, result.length());
        assertTrue(result.matches(regex));
    }

    @Test
    @DisplayName("Should delete old token and save new one")
    void shouldCreatePasswordResetToken() {
        //given
        String token = "123qwe";
        when(passwordTokenRepo.findByUser(appUser)).thenReturn(Optional.ofNullable(passwordResetToken));

        //when
        passwordServiceImpl.createPasswordResetTokenForUser(appUser, token);

        //then
        verify(passwordTokenRepo, times(1)).delete(passwordResetToken);
        verify(passwordTokenRepo, times(1)).save(any());
    }

    @Test
    @DisplayName("Should throw InvalidTokenException when token is null")
    void shouldThrowInvalidTokenException() {
        //given
        String token = "null";
        when(passwordTokenRepo.findByToken(token)).thenReturn(null);

        //when, then
        assertThrows(InvalidTokenException.class, () -> passwordServiceImpl.validatePasswordResetToken(token));
    }

    @Test
    @DisplayName("should return 'expired' when token date is expired")
    void shouldReturnExpired() {
        //given
        String token = "expired";
        when(passwordTokenRepo.findByToken(token)).thenReturn(passwordResetToken);
        when(passwordResetToken.getExpiryDate()).thenReturn(LocalDateTime.now().minusHours(4));

        //when, then
        assertThrows(InvalidTokenException.class, () -> passwordServiceImpl.validatePasswordResetToken(token));
    }

    @Test
    @DisplayName("Should change user password")
    void shouldChangeUserPassword() {
        //given
        String newPassword = "123";
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        //when
        passwordServiceImpl.changeUserPassword(appUser, newPassword);

        //then
        verify(passwordEncoder, times(1)).encode(newPassword);
        verify(appUserService, times(1)).registerAccount(appUser);
    }

}