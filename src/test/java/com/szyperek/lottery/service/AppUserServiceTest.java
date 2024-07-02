package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.PasswordResetToken;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.exception.UniqueUserEmailException;
import com.szyperek.lottery.repository.AppUserRepo;
import com.szyperek.lottery.repository.PasswordTokenRepo;
import com.szyperek.lottery.service.impl.AppUserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AppUserServiceTest {
    @Mock
    AppUser appUser;
    @Mock
    AppUserRepo appUserRepo;
    @Mock
    PasswordResetToken passwordResetToken;
    @Mock
    PasswordTokenRepo passwordTokenRepo;

    @InjectMocks
    AppUserServiceImpl appUserServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should call findAllByRole method")
    void shouldCallFindAllByRole() {
        //given
        Role role = Role.USER;

        //when
        appUserServiceImpl.getAllUsersWithUserRole();

        //then
        verify(appUserRepo, times(1)).findAllByRole(role);
    }

    @Test
    @DisplayName("Should return list of app users")
    void shouldReturnListOfAppUsers() {
        //given
        when(appUserServiceImpl.getAllUsersWithUserRole()).thenReturn(List.of(appUser));

        //when
        List<AppUser> appUsers = appUserServiceImpl.getAllUsersWithUserRole();

        //then
        assertNotNull(appUsers);
    }

    @Test
    @DisplayName("Should call findById method")
    void shouldCallFindByID() {
        //given
        Long id = 1L;

        //when
        appUserServiceImpl.getUserById(id);

        //then
        verify(appUserRepo, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return app user by id")
    void shouldReturnAppUserById() {
        //given
        Long id = 1L;
        when(appUserRepo.findById(id)).thenReturn(Optional.ofNullable(appUser));

        //when
        AppUser appUser1 = appUserServiceImpl.getUserById(id);

        //then
        assertNotNull(appUser1);
    }

    @Test
    @DisplayName("Should not return app user by id")
    void shouldNotReturnAppUserById() {
        //given
        Long id = 1L;
        when(appUserRepo.findById(id)).thenReturn(Optional.empty());

        //when
        AppUser appUser1 = appUserServiceImpl.getUserById(id);

        //then
        assertNull(appUser1);
    }

    @Test
    @DisplayName("Should call existsById and deleteById methods")
    void shouldCallTwoMethods() {
        //given
        Long id = 1L;
        when(appUserRepo.existsById(id)).thenReturn(true);

        //when
        appUserServiceImpl.deleteById(id);

        //then
        verify(appUserRepo, times(1)).existsById(id);
        verify(appUserRepo, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should call existsById method")
    void shouldCallOnlyOneMethod() {
        //given
        Long id = 1L;
        when(appUserRepo.existsById(id)).thenReturn(false);

        //when
        appUserServiceImpl.deleteById(id);

        //then
        verify(appUserRepo, times(1)).existsById(id);
        verify(appUserRepo, times(0)).deleteById(id);
    }

    @Test
    @DisplayName("Should register new account")
    void shouldRegisterAccount() {
        //given
        when(appUserRepo.save(appUser)).thenReturn(appUser);

        //when
        boolean registerStatus = appUserServiceImpl.registerAccount(appUser);

        //then
        assertTrue(registerStatus);
    }

    @Test
    @DisplayName("Should throw exception when email is currently used")
    void shouldThrowUniqueUserEmailException() {
        //given
        AppUser appUser1 = new AppUser();
        AppUser appUser2 = new AppUser();

        //when
        appUserServiceImpl.registerAccount(appUser1);
        when(appUserRepo.save(appUser2)).thenThrow(DataIntegrityViolationException.class);

        //then
        assertThrows(UniqueUserEmailException.class, () -> appUserServiceImpl.registerAccount(appUser2));

    }

    @Test
    @DisplayName("Should return app user by email")
    void shouldReturnAppUserByEmail() {
        //given
        String email = "zak@wp.pl";
        when(appUserRepo.findByEmail(email)).thenReturn(Optional.ofNullable(appUser));

        //when
        AppUser appUser = appUserServiceImpl.findByEmail(email);

        //then
        assertNotNull(appUser);
    }

    @Test
    @DisplayName("Should not return app user by email")
    void shouldNotReturnAppUserByEmail() {
        //given
        String email = "zak@wp.pl";
        when(appUserRepo.findByEmail(email)).thenReturn(Optional.empty());

        //then
        assertThrows(UsernameNotFoundException.class, () -> appUserServiceImpl.findByEmail(email));
    }

    @Test
    @DisplayName("Should return app user by password reset token")
    void shouldReturnAppUserByPasswordResetToken() {
        //given
        String token = "123";
        when(passwordTokenRepo.findByToken(token)).thenReturn(passwordResetToken);

        //when
        Optional<AppUser> appUser = appUserServiceImpl.getUserByPasswordResetToken(token);

        //then
        assertNotNull(appUser);
    }

    @Test
    @DisplayName("Should return user id based on email")
    void shouldReturnIdByEmail() {
        //given
        String email = "zak@wp.pl";
        when(appUserRepo.findIdByEmail(email)).thenReturn(1L);

        //when
        Long id = appUserServiceImpl.getUserIdByEmail(email);

        //then
        assertEquals(1L, id);
    }
}