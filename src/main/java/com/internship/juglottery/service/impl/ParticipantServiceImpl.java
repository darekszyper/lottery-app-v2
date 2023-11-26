package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.RegistrationToken;
import com.internship.juglottery.entity.enums.Status;
import com.internship.juglottery.event.RegistrationEmailEvent;
import com.internship.juglottery.exception.InvalidTokenException;
import com.internship.juglottery.exception.LotteryNotActiveException;
import com.internship.juglottery.repository.LotteryRepo;
import com.internship.juglottery.repository.ParticipantRepo;
import com.internship.juglottery.repository.RegistrationTokenRepo;
import com.internship.juglottery.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
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
        RegistrationToken myToken = new RegistrationToken(token, savedParticipant);
        registrationTokenRepo.save(myToken);

        eventPublisher.publishEvent(new RegistrationEmailEvent(contextPath, token, participant));

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
    }
}
