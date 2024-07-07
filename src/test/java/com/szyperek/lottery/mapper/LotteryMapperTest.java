package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.response.FinishedLotteryResponse;
import com.szyperek.lottery.dto.response.LotteryResponse;
import com.szyperek.lottery.entity.Lottery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.szyperek.lottery.mapper.MapperTestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LotteryMapperTest {

    private LotteryMapper lotteryMapper;

    @BeforeEach
    void setUp() {
        lotteryMapper = new LotteryMapper();
    }

    @Test
    @DisplayName("Should map LotteryRequest to Lottery")
    void shouldMapLotteryRequestToLottery() {
        // given
        LotteryRequest lotteryRequest = getBasicLotteryRequest();
        Lottery expectedResult = getBasicNotActiveLottery();

        // when
        Lottery result = lotteryMapper.mapToEntity(lotteryRequest);

        // then
        assertEquals(expectedResult.getEventName(), result.getEventName());
        assertEquals(expectedResult.getCity(), result.getCity());
        assertEquals(expectedResult.getAppUser().getId(), result.getAppUser().getId());
        assertEquals(expectedResult.getStatus(), result.getStatus());
        assertNull(result.getVouchers());
    }

    @Test
    @DisplayName("Should return null when LotteryRequest is null")
    void shouldReturnNullWhenLotteryRequestIsNull() {
        // when
        Lottery result = lotteryMapper.mapToEntity(null);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map Lottery to LotteryResponse")
    void shouldMapLotteryToLotteryResponse() {
        // given
        Lottery lottery = getBasicLottery();
        LotteryResponse expectedResult = getBasicLotteryResponse();

        // when
        LotteryResponse result = lotteryMapper.mapToResponse(lottery);

        // then
        assertEquals(expectedResult.id(), result.id());
        assertEquals(expectedResult.eventName(), result.eventName());
    }

    @Test
    @DisplayName("Should return null when Lottery is null")
    void shouldReturnNullWhenLotteryIsNull() {
        // when
        LotteryResponse result = lotteryMapper.mapToResponse(null);

        // then
        assertNull(result);
    }

    @Test
    @DisplayName("Should map Lottery to FinishedLotteryResponse")
    void shouldMapLotteryToFinishedLotteryResponse() {
        // given
        Lottery lottery = getBasicFinishedLottery();
        FinishedLotteryResponse expectedResult = getBasicFinishedLotteryResponse();

        // when
        FinishedLotteryResponse result = lotteryMapper.mapToFinishedLotteryResponse(lottery);

        // then
        assertEquals(expectedResult.id(), result.id());
        assertEquals(expectedResult.eventName(), result.eventName());
        assertEquals(expectedResult.winners(), result.winners());
        assertEquals(expectedResult.lotteryDate(), result.lotteryDate());
        assertEquals(expectedResult.city(), result.city());
    }

    @Test
    @DisplayName("Should return null when mapping FinishedLotteryResponse if Lottery is null")
    void shouldReturnNullWhenMappingFinishedLotteryResponseIfLotteryIsNull() {
        // when
        FinishedLotteryResponse result = lotteryMapper.mapToFinishedLotteryResponse(null);

        // then
        assertNull(result);
    }
}
