package com.internship.juglottery.mapper;

import com.internship.juglottery.dto.request.ParticipantRequest;
import com.internship.juglottery.dto.response.ParticipantResponse;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Winner;
import com.internship.juglottery.service.AnonymizationService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class ParticipantMapper {

    @Autowired
    private AnonymizationService anonymizationService;

    @Mapping(source = "lotteryId", target = "lottery.id")
    public abstract Participant mapParticipantRequestToParticipant(ParticipantRequest participantRequest);

    @Mapping(source = "participant.firstName", target = "firstName")
    @Mapping(source = "participant.email", target = "email")
    public abstract ParticipantResponse mapToParticipantResponse(Winner winner);

    @AfterMapping
    protected void anonymizeEmail(@MappingTarget ParticipantResponse participantResponse, Winner winner) {
        participantResponse.setEmail(anonymizationService.anonymizeEmail(winner.getParticipant().getEmail()));
    }
}
