package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.RegistrationRequest;
import com.szyperek.lottery.dto.response.AppUserResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.service.PasswordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
    @DisplayName("Should map AppUser to AppUserResponse")
    void shouldMapAppUserToAppUserResponse() {
        // given
        AppUser appUser = getBasicAppUser();
        AppUserResponse expectedResult = getBasicAppUserResponse();

        // when
        AppUserResponse result = appUserMapper.toResponse(appUser);

        // then
        assertEquals(expectedResult.id(), result.id());
        assertEquals(expectedResult.email(), result.email());
        assertEquals(expectedResult.name(), result.name());
    }

    @Test
    @DisplayName("Should return null for null AppUser")
    void shouldReturnNullForNullAppUser() {
        // when
        AppUserResponse response = appUserMapper.toResponse(null);

        // then
        assertNull(response);
    }

    @Test
    @DisplayName("Should map AppUserRequest to AppUser with generated password")
    void shouldMapAppUserRequestToAppUser() {
        // given
        RegistrationRequest registrationRequest = getBasicAppUserRequest();
        AppUser expectedResult = getBasicAppUser();

        // when
        when(passwordService.generatePassword()).thenReturn("password");
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        AppUser result = appUserMapper.toEntity(registrationRequest);

        // then
        assertEquals(expectedResult.getEmail(), result.getEmail());
        assertEquals(expectedResult.getName(), result.getName());
        assertEquals(expectedResult.getPassword(), result.getPassword());
        assertEquals(expectedResult.getRole(), result.getRole());
        assertNull(result.getPasswordResetToken());
        verify(passwordService, times(1)).generatePassword();
        verify(passwordEncoder, times(1)).encode("password");
    }

    @Test
    @DisplayName("Should return null for null AppUserRequest")
    void shouldReturnNullForNullAppUserRequest() {
        // when
        AppUser appUser = appUserMapper.toEntity(null);

        // then
        assertNull(appUser);
    }
}
