package com.internship.juglottery.controller;

import com.internship.juglottery.dto.request.ParticipantRequest;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.exception.LotteryNotActiveException;
import com.internship.juglottery.mapper.ParticipantMapper;
import com.internship.juglottery.service.ParticipantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
                                     BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("Validation error: {}", bindingResult.getAllErrors());
            return "error/participant_validation_error";
        } else if (!(participantService.isEmailAlreadyUsed(participantRequest.getLotteryId(), participantRequest.getEmail()))) {
            log.error("Validation error: email already exists");
            bindingResult.rejectValue("email", "email.exists", "Email already exists");
            return "error/participant_validation_error";
        }

        try {
            Participant participantEntity = participantMapper.mapParticipantRequestToParticipant(participantRequest);
            participantService.addParticipant(participantEntity);
        } catch (LotteryNotActiveException exception) {
            log.error(exception.getMessage());
            return "error/lottery_not_active";
        }

        return "success/participant_registration_success";
    }
}
