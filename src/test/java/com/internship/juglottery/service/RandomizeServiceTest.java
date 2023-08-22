package com.internship.juglottery.service;

import com.internship.juglottery.service.impl.RandomizeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class RandomizeServiceTest {

    @InjectMocks
    RandomizeServiceImpl randomizeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    // given
    @MethodSource("provideNumberOfParticipantsAndCorrectRange")
    @DisplayName("Should return int from given range")
    void shouldReturnIntFromGivenRange(int numberOfParticipants, List<Integer> possibleIndexes) {
        // when
        int pickedIndex = randomizeService.randomize(numberOfParticipants);
        System.out.println(pickedIndex);

        // then
        assertTrue(possibleIndexes.contains(pickedIndex));
    }

    public static Stream<Arguments> provideNumberOfParticipantsAndCorrectRange() {
        return Stream.of(
                Arguments.of(13, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)),
                Arguments.of(21, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20)),
                Arguments.of(18, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17)),
                Arguments.of(9, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8)),
                Arguments.of(5, List.of(0, 1, 2, 3, 4)),
                Arguments.of(10, List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)),
                Arguments.of(2, List.of(0, 1)),
                Arguments.of(4, List.of(0, 1, 2, 3))
        );
    }
}