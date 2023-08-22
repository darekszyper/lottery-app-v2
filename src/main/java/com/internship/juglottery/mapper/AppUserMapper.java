package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.AppUserRequest;
import com.internship.juglottery.dto.response.AppUserResponse;
import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.enums.Role;
import com.internship.juglottery.service.impl.PasswordServiceImpl;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
public abstract class AppUserMapper {

    private final Role USER_ROLE = Role.USER;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private PasswordServiceImpl passwordServiceImpl;

    public abstract AppUserResponse toResponse(AppUser appUser);

    public abstract AppUser toEntity(AppUserRequest appUserRequest);

    @BeforeMapping
    protected void setUserRoleAndEncodePassword(@MappingTarget AppUser appUser) {
        String generatedPassword = passwordServiceImpl.generatePassword();
        appUser.setPassword(passwordEncoder.encode(generatedPassword));
        appUser.setRole(USER_ROLE);
    }
}
