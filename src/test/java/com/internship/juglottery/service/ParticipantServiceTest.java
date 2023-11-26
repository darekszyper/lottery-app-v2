package com.internship.juglottery.service;


import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.RegistrationToken;
import com.internship.juglottery.entity.enums.Status;
import com.internship.juglottery.event.RegistrationEmailEvent;
import com.internship.juglottery.exception.LotteryNotActiveException;
import com.internship.juglottery.repository.LotteryRepo;
import com.internship.juglottery.repository.ParticipantRepo;
import com.internship.juglottery.repository.RegistrationTokenRepo;
import com.internship.juglottery.service.impl.ParticipantServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

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

        //when
        Participant participantEntity = participantServiceImpl.addParticipant(
                "contextPath", "token", participant);

        //then
        assertInstanceOf(Participant.class, participantEntity);
        verify(eventPublisher, times(1)).publishEvent(any(RegistrationEmailEvent.class));
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
        String email = "zak@wp.pl";
        when(participantRepo.extractEmail(lotteryId, email)).thenReturn(null);

        //when
        boolean result = participantServiceImpl.isEmailAlreadyUsedAndConfirmed(lotteryId, email);

        //then
        assertFalse(result);
    }
}