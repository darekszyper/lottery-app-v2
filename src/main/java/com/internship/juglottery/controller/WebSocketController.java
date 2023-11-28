package com.internship.juglottery.controller;

import com.internship.juglottery.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class WebSocketController {

    private final ParticipantService participantService;

    @MessageMapping("/participantCount")
    @SendTo("/topic/participantCount")
    public int sendParticipantCount(Long lotteryId) {
        return participantService.getConfirmedEmailCount(lotteryId);
    }
}
