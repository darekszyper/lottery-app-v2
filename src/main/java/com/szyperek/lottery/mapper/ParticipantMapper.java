package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.ParticipantRequest;
import com.szyperek.lottery.dto.response.ParticipantResponse;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.Winner;
import com.szyperek.lottery.service.AnonymizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParticipantMapper {

    @Autowired
    private AnonymizationService anonymizationService;

    public Participant mapParticipantRequestToParticipant(ParticipantRequest participantRequest) {
        if (participantRequest == null) {
            return null;
        }

        Participant participant = new Participant();

        participant.setLottery(participantRequestToLottery(participantRequest));
        participant.setFirstName(participantRequest.getFirstName());
        participant.setEmail(participantRequest.getEmail());

        setConfirmedEmail(participant);

        return participant;
    }

    public ParticipantResponse mapToParticipantResponse(Winner winner) {
        if (winner == null) {
            return null;
        }

        ParticipantResponse participantResponse = new ParticipantResponse();

        participantResponse.setFirstName(winnerParticipantFirstName(winner));
        participantResponse.setEmail(winnerParticipantEmail(winner));
        participantResponse.setId(winner.getId());

        anonymizeEmail(participantResponse, winner);

        return participantResponse;
    }

    protected Lottery participantRequestToLottery(ParticipantRequest participantRequest) {
        if (participantRequest == null) {
            return null;
        }

        Lottery lottery = new Lottery();

        lottery.setId(participantRequest.getLotteryId());

        return lottery;
    }

    private String winnerParticipantFirstName(Winner winner) {
        if (winner == null) {
            return null;
        }
        Participant participant = winner.getParticipant();
        if (participant == null) {
            return null;
        }
        String firstName = participant.getFirstName();
        if (firstName == null) {
            return null;
        }
        return firstName;
    }

    private String winnerParticipantEmail(Winner winner) {
        if (winner == null) {
            return null;
        }
        Participant participant = winner.getParticipant();
        if (participant == null) {
            return null;
        }
        String email = participant.getEmail();
        if (email == null) {
            return null;
        }
        return email;
    }

    protected void setConfirmedEmail(Participant participant) {
        participant.setEmailConfirmed(true);
    }

    protected void anonymizeEmail(ParticipantResponse participantResponse, Winner winner) {
        participantResponse.setEmail(anonymizationService.anonymizeEmail(winner.getParticipant().getEmail()));
    }
}
