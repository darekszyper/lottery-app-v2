package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.ParticipantRequest;
import com.internship.juglottery.dto.response.ParticipantResponse;
import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Winner;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-11-27T22:26:19+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.3 (JetBrains s.r.o.)"
)
@Component
public class ParticipantMapperImpl extends ParticipantMapper {

    @Override
    public Participant mapParticipantRequestToParticipant(ParticipantRequest participantRequest) {
        if ( participantRequest == null ) {
            return null;
        }

        Participant participant = new Participant();

        participant.setLottery( participantRequestToLottery( participantRequest ) );
        participant.setFirstName( participantRequest.getFirstName() );
        participant.setEmail( participantRequest.getEmail() );

        return participant;
    }

    @Override
    public ParticipantResponse mapToParticipantResponse(Winner winner) {
        if ( winner == null ) {
            return null;
        }

        ParticipantResponse participantResponse = new ParticipantResponse();

        participantResponse.setFirstName( winnerParticipantFirstName( winner ) );
        participantResponse.setEmail( winnerParticipantEmail( winner ) );
        participantResponse.setId( winner.getId() );

        anonymizeEmail( participantResponse, winner );

        return participantResponse;
    }

    protected Lottery participantRequestToLottery(ParticipantRequest participantRequest) {
        if ( participantRequest == null ) {
            return null;
        }

        Lottery lottery = new Lottery();

        lottery.setId( participantRequest.getLotteryId() );

        return lottery;
    }

    private String winnerParticipantFirstName(Winner winner) {
        if ( winner == null ) {
            return null;
        }
        Participant participant = winner.getParticipant();
        if ( participant == null ) {
            return null;
        }
        String firstName = participant.getFirstName();
        if ( firstName == null ) {
            return null;
        }
        return firstName;
    }

    private String winnerParticipantEmail(Winner winner) {
        if ( winner == null ) {
            return null;
        }
        Participant participant = winner.getParticipant();
        if ( participant == null ) {
            return null;
        }
        String email = participant.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
