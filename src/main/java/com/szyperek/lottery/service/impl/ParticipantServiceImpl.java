package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.RegistrationToken;
import com.szyperek.lottery.entity.enums.Status;
import com.szyperek.lottery.exception.InvalidTokenException;
import com.szyperek.lottery.exception.LotteryNotActiveException;
import com.szyperek.lottery.repository.LotteryRepo;
import com.szyperek.lottery.repository.ParticipantRepo;
import com.szyperek.lottery.repository.RegistrationTokenRepo;
import com.szyperek.lottery.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Setter
@Transactional
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
    private final ParticipantRepo participantRepo;
    private final LotteryRepo lotteryRepo;
    private final RegistrationTokenRepo registrationTokenRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final SimpMessagingTemplate template;

    @Override
    public List<Participant> getParticipantsByLotteryId(Long lotteryId) {
        return new ArrayList<>(participantRepo.findByLotteryIdAndEmailConfirmed(lotteryId));
    }

    @Override
    public Participant addParticipant(String contextPath, String token, Participant participant) {
        if (!isLotteryStatusActive(participant.getLottery().getId()))
            throw new LotteryNotActiveException("Lottery with id: " +
                    participant.getLottery().getId() + " is not active");

        Participant savedParticipant = participantRepo.save(participant);
        /**
         * Registration for lottery confirmation email
         */
        /*
        RegistrationToken myToken = new RegistrationToken(token, savedParticipant);
        registrationTokenRepo.save(myToken);

        eventPublisher.publishEvent(new RegistrationEmailEvent(contextPath, token, participant));
         */
        Long lotteryId = participant.getLottery().getId();
        template.convertAndSend("/topic/participantCount", this.getConfirmedEmailCount(lotteryId));

        return savedParticipant;
    }

    @Override
    public boolean isLotteryStatusActive(Long lotteryId) {
        Status status = lotteryRepo.getStatusFromDb(lotteryId);
        return status == Status.ACTIVE;
    }

    @Override
    public void removeParticipantId(Long lotteryId) {
        participantRepo.removeParticipantId(lotteryId);
    }

    @Override
    public boolean isEmailAlreadyUsedAndConfirmed(Long lotteryId, String email) {
        return participantRepo.isEmailAlreadyUsedAndConfirmed(lotteryId, email);
    }

    @Override
    public void confirmEmail(String token) {
        RegistrationToken registrationToken = registrationTokenRepo.findByToken(token);

        if (registrationToken == null)
            throw new InvalidTokenException("Invalid registration token");

        Participant participant = registrationToken.getParticipant();

        String email = participant.getEmail();
        Long lotteryId = participant.getLottery().getId();

        if (this.isEmailAlreadyUsedAndConfirmed(lotteryId, email))
            throw new InvalidTokenException("Email is already confirmed");

        participant.setEmailConfirmed(true);
        participantRepo.save(participant);

        template.convertAndSend("/topic/participantCount", this.getConfirmedEmailCount(lotteryId));
    }

    public int getConfirmedEmailCount(Long lotteryId) {
        return participantRepo.countByIsEmailConfirmedTrueAndLotteryId(lotteryId);
    }
}
