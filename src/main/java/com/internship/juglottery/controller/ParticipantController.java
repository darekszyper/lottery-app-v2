package com.internship.juglottery.controller;

import com.internship.juglottery.dto.request.ParticipantRequest;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.exception.InvalidTokenException;
import com.internship.juglottery.exception.LotteryNotActiveException;
import com.internship.juglottery.mapper.ParticipantMapper;
import com.internship.juglottery.service.ParticipantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("register")
@RequiredArgsConstructor
@Slf4j
public class ParticipantController {

    private final ParticipantService participantService;
    private final ParticipantMapper participantMapper;

    @GetMapping("{lotteryId}")
    public String getRegistrationForm(Model model, @PathVariable Long lotteryId) {
        model.addAttribute("participant", new ParticipantRequest());
        model.addAttribute("lotteryId", lotteryId);
        return "registration_form";
    }

    @PostMapping
    public String registerForLottery(@ModelAttribute("participant") @Valid ParticipantRequest participantRequest,
                                     HttpServletRequest request,
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/participant_validation_error";
        } else if ((participantService.isEmailAlreadyUsedAndConfirmed(participantRequest.getLotteryId(), participantRequest.getEmail()))) {
            log.error("Validation error: email already exists");
            bindingResult.rejectValue("email", "email.exists", "Email already exists");
            return "error/participant_validation_error";
        }

        String token = UUID.randomUUID().toString();
        String hostName = request.getRequestURL().toString();
        try {
            Participant participantEntity = participantMapper.mapParticipantRequestToParticipant(participantRequest);
            participantService.addParticipant(hostName, token, participantEntity);
        } catch (LotteryNotActiveException exception) {
            log.error(exception.getMessage());
            return "error/lottery_not_active";
        }

        return "success/participant_registration_success";
    }

    @GetMapping("/confirm_email")
    public String confirmEmail(@RequestParam("token") String token) {
        try {
            participantService.confirmEmail(token);
        } catch (InvalidTokenException e) {
            log.error(e.getMessage());
            return "redirect:/login";
        }
        return "success/registration_email_confirmation_success";
    }
}
