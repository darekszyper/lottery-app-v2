package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.entity.Winner;
import com.internship.juglottery.repository.WinnerRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.List;

import static org.mockito.Mockito.*;

class EmailSenderServiceTest {

    @Mock
    JavaMailSender javaMailSender;

    @Mock
    Winner winner;

    @Mock
    WinnerRepo winnerRepo;

    @Mock
    AppUser appUser;

    @InjectMocks
    EmailSenderService emailSenderService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should send vouchers to winners")
    void shouldSentVouchersToWinners() {
        //when
        Long lotteryId = 1L;
        when(winnerRepo.findByLotteryId(lotteryId)).thenReturn(List.of(winner));
        when(winner.getParticipant()).thenReturn(new Participant());
        when(winner.getVoucher()).thenReturn(new Voucher());

        //when
        emailSenderService.sendVouchersToWinners(lotteryId);

        //then
        verify(winnerRepo, times(1)).findByLotteryId(lotteryId);
    }

    @Test
    @DisplayName("Should sent email to winner")
    void shouldSentEmailToWinner() {
        //given
        when(winner.getParticipant()).thenReturn(new Participant());
        when(winner.getVoucher()).thenReturn(new Voucher());

        //when
        emailSenderService.sendVoucherToWinner(winner);

        //then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should sent email for password reset")
    void shouldSentEmailForPasswordReset() {
        //given
        String contextPath = "localhost:/8080";
        String token = "123qwe";

        //when
        emailSenderService.sendResetPasswordInstruction(contextPath, token, appUser);

        //then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should sent email when account is created")
    void shouldSentEmailWhenAccountIsCreated() {
        //given
        String contextPath = "localhost:/8080";

        //when
        emailSenderService.sendAccountActivationInstruction(contextPath, appUser);

        //then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    @DisplayName("Should sent greeting email")
    void shouldSentGreetingEmail() {
        //when TODO: refactor
        emailSenderService.sendRegistrationForLotteryConfirmationLink(new Participant());

        //then
        verify(javaMailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}