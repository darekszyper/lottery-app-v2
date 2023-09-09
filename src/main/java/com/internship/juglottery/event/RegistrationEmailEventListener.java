package com.internship.juglottery.event;

import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.service.impl.EmailSenderService;
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
