package com.internship.juglottery.service;

import com.internship.juglottery.entity.Participant;

import java.util.List;

public interface ParticipantService {
    List<Participant> getParticipantsByLotteryId(Long id);

    Participant addParticipant(Participant participant);

    boolean isLotteryStatusActive(Long lotteryId);

    void removeParticipantId(Long lotteryId);

    boolean isEmailAlreadyUsed(Long lotteryId, String email);
}
