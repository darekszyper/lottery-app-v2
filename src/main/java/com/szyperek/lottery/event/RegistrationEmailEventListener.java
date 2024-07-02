package com.szyperek.lottery.event;

import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.service.impl.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RegistrationEmailEventListener {

    private final EmailSenderService emailSenderService;

    @EventListener
    public void onApplicationEvent(RegistrationEmailEvent event) {
        String contextPath = event.contextPath();
        String token = event.token();
        Participant participant = event.participant();
        emailSenderService.sendRegistrationForLotteryConfirmationLink(contextPath, token, participant);

    }
}
