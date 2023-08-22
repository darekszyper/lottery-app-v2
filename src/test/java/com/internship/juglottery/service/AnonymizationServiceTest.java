package com.internship.juglottery.service;

import com.internship.juglottery.repository.ParticipantRepo;
import com.internship.juglottery.service.impl.AnonymizationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.*;

class AnonymizationServiceTest {

    @Mock
    ParticipantRepo participantRepo;

    @InjectMocks
    private AnonymizationServiceImpl anonymizationServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideEmailsAndMaskedEmails")
    @DisplayName("Should properly mask letters before at in email")
    void shouldProperlyMaskLettersBeforeAtInEmail(String email, String result) {
        // when
        String maskedEmail = anonymizationServiceImpl.anonymizeEmail(email);

        //then
        assertEquals(result, maskedEmail);
    }

    public static Stream<Arguments> provideEmailsAndMaskedEmails() {
        return Stream.of(
                arguments("marek@wp.pl", "m***k@wp.pl"),
                arguments("kamilrfv@wp.com.pl", "k******v@wp.com.pl"),
                arguments("mk@gmail.com", "m*@gmail.com"),
                arguments("mak@gmail.com", "m*k@gmail.com"),
                arguments("m@wp.pl", "*@wp.pl"),
                arguments("marekczarekkamil@wp.com.pl", "m**************l@wp.com.pl")
        );
    }

    @Test
    @DisplayName("Should verify that participant repo method are used")
    void shouldVerifyThatParticipantRepoMethodAreUsed() {
        //given
        when(participantRepo.extractIdsAndEmails()).thenReturn(List.of("1,marek@wp.pl"));

        //when
        anonymizationServiceImpl.cronAnonymize();

        //then
        verify(participantRepo, times(1)).extractIdsAndEmails();
        verify(participantRepo, times(1)).editEmails(1L, "m***k@wp.pl");
    }

}