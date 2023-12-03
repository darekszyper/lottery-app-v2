package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.event.RegistrationEmailEvent;
import com.internship.juglottery.service.ParticipantService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmailSendingIntegrationTests {

    @Autowired
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
