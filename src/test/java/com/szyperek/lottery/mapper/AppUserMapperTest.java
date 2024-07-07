package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.AppUserRequest;
import com.szyperek.lottery.dto.response.AppUserResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.service.PasswordService;
import com.szyperek.lottery.service.impl.PasswordServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.szyperek.lottery.mapper.MapperTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class AppUserMapperTest {

    @InjectMocks
    private AppUserMapper appUserMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PasswordService passwordService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldMapAppUserToAppUserResponse() {
        // given
        AppUser appUser = getBasicAppSuperUser();

        // when
        AppUserResponse response = appUserMapper.toResponse(appUser);

        // then

//        appUser.setId(1L);
//        appUser.setEmail("test@example.com");
//        appUser.setName("Test User");
//
//
//        assertEquals(1L, response.getId());
//        assertEquals("test@example.com", response.getEmail());
//        assertEquals("Test User", response.getName());
    }

    @Test
    void shouldReturnNullForNullAppUser() {
        // when
        AppUserResponse response = appUserMapper.toResponse(null);

        // then
        assertNull(response);
    }

    @Test
    void shouldMapAppUserRequestToAppUser() {
        // given
        AppUserRequest appUserRequest = getBasicAppUserRequest();
        AppUser expectedResult = getBasicAppUser();

        // when
        AppUser result = appUserMapper.toEntity(appUserRequest);
        when(passwordService.generatePassword()).thenReturn("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // then
        assertEquals(expectedResult.getEmail(), result.getEmail());
        assertEquals("Test User", appUser.getName());
        assertEquals("encodedPassword", appUser.getPassword());
        assertEquals(Role.USER, appUser.getRole());
    }

    @Test
    void shouldReturnNullForNullAppUserRequest() {
        // when
        AppUser appUser = appUserMapper.toEntity(null);

        // then
        assertNull(appUser);
    }
}