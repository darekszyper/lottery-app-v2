package com.szyperek.lottery.dto.response;

import java.time.LocalDate;
import java.util.List;

public record FinishedLotteryResponse(
        Long id,
        String eventName,
        List<WinnerResponse> winners,
        LocalDate lotteryDate,
        String city
) {
}
