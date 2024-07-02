package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.Participant;

import java.util.List;

public interface ParticipantService {
    List<Participant> getParticipantsByLotteryId(Long id);

    Participant addParticipant(String contextPath, String token, Participant participant);

    boolean isLotteryStatusActive(Long lotteryId);

    void removeParticipantId(Long lotteryId);

    boolean isEmailAlreadyUsedAndConfirmed(Long lotteryId, String email);

    void confirmEmail(String token);

    int getConfirmedEmailCount(Long lotteryId);
}
