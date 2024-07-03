package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.AppUserRequest;
import com.szyperek.lottery.dto.response.AppUserResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.service.impl.PasswordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {

    private final Role USER_ROLE = Role.USER;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordServiceImpl passwordServiceImpl;

    public AppUserResponse toResponse(AppUser appUser) {
        if (appUser == null) {
            return null;
        }

        Long id = null;
        String email = null;
        String name = null;

        id = appUser.getId();
        email = appUser.getEmail();
        name = appUser.getName();

        AppUserResponse appUserResponse = new AppUserResponse(id, email, name);

        return appUserResponse;
    }

    public AppUser toEntity(AppUserRequest appUserRequest) {
        if (appUserRequest == null) {
            return null;
        }

        AppUser appUser = new AppUser();

        setUserRoleAndEncodePassword(appUser);

        appUser.setEmail(appUserRequest.getEmail());
        appUser.setName(appUserRequest.getName());

        return appUser;
    }

    protected void setUserRoleAndEncodePassword(AppUser appUser) {
        String generatedPassword = passwordServiceImpl.generatePassword();
        appUser.setPassword(passwordEncoder.encode(generatedPassword));
        appUser.setRole(USER_ROLE);
    }
}
