package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.ParticipantRequest;
import com.szyperek.lottery.dto.response.ParticipantResponse;
import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.Winner;
import com.szyperek.lottery.service.AnonymizationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.szyperek.lottery.mapper.MapperTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ParticipantMapperTest {

    private static final String ANONYMIZED_EMAIL = "e***l@gmail.com";

    @InjectMocks
    private ParticipantMapper participantMapper;

    @Mock
    private AnonymizationService anonymizationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should map ParticipantRequest to Participant")
    void shouldMapParticipantRequestToParticipant() {
        // given
        ParticipantRequest participantRequest = getBasicParticipantRequest();
        Participant expectedResult = getBasicParticipant();

        // when
        Participant result = participantMapper.mapParticipantRequestToParticipant(participantRequest);

        // then
        assertEquals(expectedResult.getFirstName(), result.getFirstName());
        assertEquals(expectedResult.getEmail(), result.getEmail());
        assertEquals(expectedResult.getLottery().getId(), result.getLottery().getId());
        assertEquals(expectedResult.isEmailConfirmed(), result.isEmailConfirmed());
    }

    @Test
    @DisplayName("Should return null when ParticipantRequest is null")
    void shouldReturnNullWhenParticipantRequestIsNull() {
        // when
        Participant result = participantMapper.mapParticipantRequestToParticipant(null);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map Winner to ParticipantResponse with anonymized email")
    void shouldMapWinnerToParticipantResponse() {
        // given
        Winner winner = getBasicWinner();
        ParticipantResponse expectedResult = getBasicParticipantResponse();
        expectedResult.setEmail(ANONYMIZED_EMAIL);

        // when
        when(anonymizationService.anonymizeEmail(winner.getParticipant().getEmail()))
                .thenReturn(ANONYMIZED_EMAIL);

        ParticipantResponse result = participantMapper.mapToParticipantResponse(winner);

        // then
        assertEquals(expectedResult.getFirstName(), result.getFirstName());
        assertEquals(expectedResult.getEmail(), result.getEmail());
        assertEquals(expectedResult.getId(), result.getId());
    }

    @Test
    @DisplayName("Should return null when Winner is null")
    void shouldReturnNullWhenWinnerIsNull() {
        // when
        ParticipantResponse result = participantMapper.mapToParticipantResponse(null);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("Should call AnonymizationService to anonymize email in ParticipantResponse")
    void shouldAnonymizeEmailInParticipantResponse() {
        // given
        Winner winner = getBasicWinner();

        // when
        when(anonymizationService.anonymizeEmail(winner.getParticipant().getEmail()))
                .thenReturn(ANONYMIZED_EMAIL);

        ParticipantResponse result = participantMapper.mapToParticipantResponse(winner);

        // then
        assertEquals(ANONYMIZED_EMAIL, result.getEmail());
        verify(anonymizationService, times(1)).anonymizeEmail(winner.getParticipant().getEmail());
    }
}
