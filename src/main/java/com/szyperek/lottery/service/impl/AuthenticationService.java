package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.dto.request.AuthenticationRequest;
import com.szyperek.lottery.dto.request.RegistrationRequest;
import com.szyperek.lottery.dto.response.AuthenticationResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.exception.UniqueUserEmailException;
import com.szyperek.lottery.mapper.AppUserMapper;
import com.szyperek.lottery.repository.AppUserRepo;
import com.szyperek.lottery.repository.TokenRepo;
import com.szyperek.lottery.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;//TODO: imlement changing password
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepo tokenRepo;
    private final AppUserMapper appUserMapper;
    private final EmailSenderService emailSenderService;

    public void register(
            RegistrationRequest registrationRequest,
            HttpServletRequest httpServletRequest //TODO: check if this works with REST
    ) {
        AppUser appUser = appUserMapper.toEntity(registrationRequest);
        try {
            appUserRepo.save(appUser);
        } catch (DataIntegrityViolationException e) {
            throw new UniqueUserEmailException("Email already exists in DB");
        }

        String hostName = httpServletRequest.getRequestURL().toString();
        hostName = hostName.replace(httpServletRequest.getServletPath(), "");
        emailSenderService.sendAccountActivationInstruction(hostName, appUser);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        HashMap<String, Object> claims = new HashMap<>();
        AppUser appUser = ((AppUser) auth.getPrincipal());
        claims.put("name", appUser.getName());

        String jwtToken = jwtService.generateToken(claims, appUser);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}