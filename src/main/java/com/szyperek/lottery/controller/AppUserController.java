package com.szyperek.lottery.controller;

import com.szyperek.lottery.dto.request.RegistrationRequest;
import com.szyperek.lottery.dto.response.AppUserResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.exception.UniqueUserEmailException;
import com.szyperek.lottery.mapper.AppUserMapper;
import com.szyperek.lottery.service.AppUserService;
import com.szyperek.lottery.service.impl.EmailSenderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/account_management")
public class AppUserController {

    private final AppUserService appUserService;
    private final AppUserMapper appUserMapper;
    private final EmailSenderService emailSenderService;

    @GetMapping
    public String showAccountManagement(Model model) {
        List<AppUserResponse> users = appUserService.getAllUsersWithUserRole().stream()
                .map(appUserMapper::toResponse)
                .toList();
        model.addAttribute("users", users);
        return "account_management";
    }

    @GetMapping("/create")
    public String showCreateNewAccount(Model model) {
        model.addAttribute("user", new RegistrationRequest());
        return "create_account";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("user") @Valid RegistrationRequest registrationRequest,
                                BindingResult bindingResult,
                                Model model,
                                HttpServletRequest request) {
        AppUser appUser = appUserMapper.toEntity(registrationRequest);
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/account_validation_error";
        }
        try {
            appUserService.registerAccount(appUser);
        } catch (UniqueUserEmailException e) {
            model.addAttribute("dbError", e.getMessage());
            return "error/account_validation_error";
        }
        String hostName = request.getRequestURL().toString();
        hostName = hostName.replace(request.getServletPath(), "");
        emailSenderService.sendAccountActivationInstruction(hostName, appUser);
        return "redirect:/account_management";
    }

    @PostMapping("/delete/{userId}")
    public String deleteUser(@PathVariable Long userId) {
        appUserService.deleteById(userId);
        return "redirect:/account_management";
    }
}
