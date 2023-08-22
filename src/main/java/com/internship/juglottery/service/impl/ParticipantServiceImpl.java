package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.enums.Status;
import com.internship.juglottery.event.RegistrationEmailEvent;
import com.internship.juglottery.exception.LotteryNotActiveException;
import com.internship.juglottery.repository.LotteryRepo;
import com.internship.juglottery.repository.ParticipantRepo;
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
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public List<Participant> getParticipantsByLotteryId(Long lotteryId) {
        return new ArrayList<>(participantRepo.findByLotteryId(lotteryId));
    }

    @Override
    public Participant addParticipant(Participant participant) {
        if (!isLotteryStatusActive(participant.getLottery().getId()))
            throw new LotteryNotActiveException("Lottery with id: " + participant.getLottery().getId() + " is not active");

        eventPublisher.publishEvent(new RegistrationEmailEvent(participant));

        return participantRepo.save(participant);
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
    public boolean isEmailAlreadyUsed(Long lotteryId, String email) {
        String extractedEmail = participantRepo.extractEmail(lotteryId, email);
        return extractedEmail == null || extractedEmail.isEmpty();
    }


}
