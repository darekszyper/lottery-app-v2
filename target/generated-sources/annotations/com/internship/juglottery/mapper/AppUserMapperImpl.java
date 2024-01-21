package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.AppUserRequest;
import com.internship.juglottery.dto.response.AppUserResponse;
import com.internship.juglottery.entity.AppUser;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-21T15:07:44+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (JetBrains s.r.o.)"
)
@Component
public class AppUserMapperImpl extends AppUserMapper {

    @Override
    public AppUserResponse toResponse(AppUser appUser) {
        if ( appUser == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        String name = null;

        id = appUser.getId();
        email = appUser.getEmail();
        name = appUser.getName();

        AppUserResponse appUserResponse = new AppUserResponse( id, email, name );

        return appUserResponse;
    }

    @Override
    public AppUser toEntity(AppUserRequest appUserRequest) {
        if ( appUserRequest == null ) {
            return null;
        }

        AppUser appUser = new AppUser();

        setUserRoleAndEncodePassword( appUser );

        appUser.setEmail( appUserRequest.getEmail() );
        appUser.setName( appUserRequest.getName() );

        return appUser;
    }
}
