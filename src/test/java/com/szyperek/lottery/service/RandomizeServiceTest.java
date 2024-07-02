package com.szyperek.lottery.service;

import com.szyperek.lottery.service.impl.RandomizeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void shouldReturnIntFromGivenRange(int range, List<Integer> possibleIndexes, int amountOfNumbers) {
        // when
        List<Integer> pickedIndexes = randomizeService.randomize(range, amountOfNumbers);
        Set<Integer> uniqueSet = new HashSet<>(pickedIndexes);
        System.out.println(pickedIndexes);

        // then
        //picked indexes should contain only possible indexes
        assertTrue(possibleIndexes.containsAll(pickedIndexes));
        //amount of picked numbers should be correct
        assertEquals(pickedIndexes.size(), amountOfNumbers);
        //there should be no duplicates
        assertEquals(uniqueSet.size(), pickedIndexes.size());
    }

    public static Stream<Arguments> provideNumberOfParticipantsAndCorrectRange() {
        return Stream.of(
                Arguments.of(12,
                        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12), 3),
                Arguments.of(20,
                        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20), 4),
                Arguments.of(17,
                        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17), 6),
                Arguments.of(8,
                        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8), 3),
                Arguments.of(4,
                        List.of(0, 1, 2, 3, 4), 5),
                Arguments.of(9,
                        List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9), 1),
                Arguments.of(1,
                        List.of(0, 1), 2),
                Arguments.of(3,
                        List.of(0, 1, 2, 3), 1)
        );
    }
}