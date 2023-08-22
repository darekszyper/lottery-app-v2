package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.AppUser;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.entity.Winner;
import com.internship.juglottery.repository.WinnerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailSenderService {

    @Value("${MAIL_USERNAME}")
    private String EMAIL;

    private final JavaMailSender mailSender;
    private final WinnerRepo winnerRepo;

    private SimpleMailMessage createBasicMailMessage(String subject, String body, String recipientEmail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EMAIL);
        message.setTo(recipientEmail);
        message.setSubject(subject);
        message.setText(body);
        return message;
    }

    public void sendVouchersToWinners(Long lotteryId) {
        List<Winner> winners = winnerRepo.findByLotteryId(lotteryId);
        if (!winners.isEmpty()) {
            winners.forEach(this::sendVoucherToWinner);
        }
    }

    public void sendVoucherToWinner(Winner winner) {
        String recipientEmail = winner.getParticipant().getEmail();
        Voucher voucher = winner.getVoucher();

        String subject = "🎉 Congratulations! Here's Your Voucher Code 🎈";
        String body = "Hello " + winner.getParticipant().getFirstName() + "! ✨ \n\n" +
                "Congratulations on your victory!\n\n" +
                "Your exclusive voucher " + voucher.getVoucherName() +
                " code is: " + voucher.getActivationCode() + " 🎈\n\n" +
                "Expiration date: " + voucher.getExpirationDate() + "\n\n" +
                "Wishing you happy coding!\n\n" +
                "Best regards,\n" +
                "JUG App Team";

        SimpleMailMessage message = createBasicMailMessage(subject, body, recipientEmail);
        mailSender.send(message);
    }

    public void sendResetPasswordInstruction(String contextPath, String token, AppUser appUser) {
        String subject = "Reset password";
        String url = contextPath + "/change_password?token=" + token;
        String body = "Hello " + appUser.getName() + "!\n\n" +
                "Go to this URL to change your password:\n" + url +
                "\n\nBest regards,\n" +
                "JUG App Team";

        SimpleMailMessage message = createBasicMailMessage(subject, body, appUser.getEmail());
        mailSender.send(message);
    }

    public void sendAccountActivationInstruction(String contextPath, AppUser appUser) {
        String subject = "Your account has been created";
        String url = contextPath + "/login";
        String body = "Hello " + appUser.getName() + "!\n\n" +
                "Go to this URL and click on \"Reset password\" to activate your account:\n" + url +
                "\n\nBest regards,\n" +
                "JUG App Team";

        SimpleMailMessage message = createBasicMailMessage(subject, body, appUser.getEmail());
        mailSender.send(message);
    }

    public void sendRegistrationForLotteryConfirmation(Participant participant) {
        String subject = "Joining confirmation";
        String body = "Hello " + participant.getFirstName() + "!\n\n" +
                "You have been added to a lottery:\n" +
                "\nBest regards,\n" +
                "JUG App Team";

        SimpleMailMessage message = createBasicMailMessage(subject, body, participant.getEmail());
        mailSender.send(message);
    }
}

