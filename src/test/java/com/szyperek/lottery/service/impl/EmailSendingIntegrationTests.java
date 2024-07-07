package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.event.RegistrationEmailEvent;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

@SpringBootTest
class EmailSendingIntegrationTests {

    private ApplicationEventPublisher eventPublisher;

    @Test
    @Disabled
    public void testSendRegistrationEmails() throws InterruptedException {
        int numberOfEmails = 30;

        for (int i = 0; i < numberOfEmails; i++) {
            String randomEmail = "test" + i + "//" + UUID.randomUUID() + "@gmail.com";
            Participant participant = new Participant();
            participant.setEmail(randomEmail);
            participant.setFirstName("name" + i);
            String token = UUID.randomUUID().toString();
            String contextPath = "http://localhost:8080";

            eventPublisher.publishEvent(new RegistrationEmailEvent(contextPath, token, participant));
        }
        System.out.println("*************ALL EVENTS ARE PUBLISHED**********************");

        // Wait for some time to allow all emails to be sent
        Thread.sleep(30000);
    }
}
