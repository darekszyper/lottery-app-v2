package com.szyperek.lottery.service;


import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.RegistrationToken;
import com.szyperek.lottery.entity.enums.Status;
import com.szyperek.lottery.exception.LotteryNotActiveException;
import com.szyperek.lottery.repository.LotteryRepo;
import com.szyperek.lottery.repository.ParticipantRepo;
import com.szyperek.lottery.repository.RegistrationTokenRepo;
import com.szyperek.lottery.service.impl.ParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ParticipantServiceTest {

    @Mock
    LotteryRepo lotteryRepo;
    @Mock
    ParticipantRepo participantRepo;
    @Mock
    RegistrationTokenRepo registrationTokenRepo;
    @Mock
    Participant participant;
    @Mock
    Lottery lottery;
    @Mock
    ApplicationEventPublisher eventPublisher;
    @Mock
    SimpMessagingTemplate template;

    @InjectMocks
    ParticipantServiceImpl participantServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should return true if lottery status is active")
    void shouldReturnTrueIfLotteryStatusIsActive() {
        //given
        Long lotteryId = 1L;
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.ACTIVE);

        //when
        boolean status = participantServiceImpl.isLotteryStatusActive(lotteryId);

        //then
        assertTrue(status);
    }

    @Test
    @DisplayName("Should return list of participants")
    void shouldReturnListOfParticipants() {
        //given
        Long lotteryId = 1L;
        List<Participant> participants1 = new ArrayList<>();
        participants1.add(new Participant("ss", "dd@dd.dd"));
        participants1.add(new Participant("pp", "pp@dd.dd"));
        when(participantRepo.findByLotteryIdAndEmailConfirmed(lotteryId)).thenReturn(participants1);

        //when
        List<Participant> participants = participantServiceImpl.getParticipantsByLotteryId(lotteryId);

        //then
        assertEquals(2, participants.size());
    }

    @Test
    @DisplayName("Should throw lottery not active exception when lottery is not active")
    void shouldThrowLotteryNotActiveException() {
        //given
        Long lotteryId = 1L;
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.NOT_ACTIVE);
        when(participant.getLottery()).thenReturn(lottery);
        when(participant.getLottery().getId()).thenReturn(lotteryId);

        //then
        assertThrows(LotteryNotActiveException.class, () -> participantServiceImpl.addParticipant(
                "contextPath", "token", participant));
    }

    @Test
    @DisplayName("Should add participant when lottery is active")
    void shouldAddParticipantWhenLotteryStatusIsActive() {
        //given
        Long lotteryId = 1L;
        when(registrationTokenRepo.save(any(RegistrationToken.class))).thenReturn(new RegistrationToken());
        when(lotteryRepo.getStatusFromDb(lotteryId)).thenReturn(Status.ACTIVE);
        when(participant.getLottery()).thenReturn(lottery);
        when(participant.getLottery().getId()).thenReturn(lotteryId);
        when(participantRepo.save(participant)).thenReturn(participant);
        doNothing().when(template).convertAndSend(anyString(), anyInt());

        //when
        Participant participantEntity = participantServiceImpl.addParticipant(
                "contextPath", "token", participant);

        //then
        assertInstanceOf(Participant.class, participantEntity);
        // verify(eventPublisher, times(1)).publishEvent(any(RegistrationEmailEvent.class));
        verify(participantRepo, times(1)).save(participantEntity);
    }

    @Test
    @DisplayName("Should call removeParticipantId method")
    void shouldCallRemoveParticipant() {
        //given
        Long lotteryId = 1L;

        //when
        participantServiceImpl.removeParticipantId(lotteryId);

        //then
        verify(participantRepo, times(1)).removeParticipantId(lotteryId);
    }

    @Test
    @DisplayName("Should return false if email is not already used")
    void shouldReturnFalseIfEmailNotUsed() {
        //given
        Long lotteryId = 1L;
        String email = "email@gmail.com";
        when(participantRepo.isEmailAlreadyUsedAndConfirmed(lotteryId, email)).thenReturn(false);

        //when
        boolean result = participantServiceImpl.isEmailAlreadyUsedAndConfirmed(lotteryId, email);

        //then
        assertFalse(result);
    }
}