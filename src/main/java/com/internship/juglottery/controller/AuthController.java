package com.internship.juglottery.controller;

import com.internship.juglottery.dto.request.EmailRequest;
import com.internship.juglottery.dto.request.ResetPasswordRequest;
import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.service.AppUserService;
import com.internship.juglottery.service.impl.EmailSenderService;
import com.internship.juglottery.service.impl.PasswordServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.UUID;

import static com.internship.juglottery.entity.enums.Role.SUPER_USER;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final AppUserService appUserService;
    private final PasswordServiceImpl passwordService;
    private final EmailSenderService emailSenderService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin() {
        return "login";
    }

    @GetMapping("/default")
    public String defaultAfterLogin(HttpServletRequest request) {
        if (request.isUserInRole(SUPER_USER.name())) {
            return "redirect:/account_management";
        }
        return "redirect:/lottery";
    }

    @PostMapping("/reset_password")
    public String resetPassword(HttpServletRequest request,
                                @RequestParam("email") EmailRequest emailRequest) {
        AppUser appUser = appUserService.findByEmail(emailRequest.getEmail());
        String token = UUID.randomUUID().toString();
        passwordService.createPasswordResetTokenForUser(appUser, token);

        String hostName = request.getRequestURL().toString();
        hostName = hostName.replace(request.getServletPath(), "");
        emailSenderService.sendResetPasswordInstruction(hostName, token, appUser);
        return "success/reset_password_success";
    }

    @GetMapping("/reset_password")
    public String showForgotPassword(Model model) {
        model.addAttribute("emailRequest", new EmailRequest());
        return "reset_password";
    }

    @GetMapping("/change_password")
    public String showChangePasswordPage(Model model, @RequestParam("token") String token) {
        String result = passwordService.validatePasswordResetToken(token);
        if (result != null) {
            return "redirect:/login";
        } else {
            model.addAttribute("token", token);
            model.addAttribute("changePassword", new ResetPasswordRequest());
            return "change_password";
        }
    }

    @PostMapping("/change_password")
    public String savePassword(@Valid @ModelAttribute("changePassword") ResetPasswordRequest resetPasswordRequest,
                               BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/change_password_error";
        }

        String result = passwordService.validatePasswordResetToken(resetPasswordRequest.getToken());

        if(result != null) {
            return "redirect:/login";
        }

        Optional<AppUser> appUser = appUserService.getUserByPasswordResetToken(resetPasswordRequest.getToken());
        appUser.ifPresent(user -> passwordService.changeUserPassword(user, resetPasswordRequest.getNewPassword()));
        return "success/change_password_success";
    }
}
