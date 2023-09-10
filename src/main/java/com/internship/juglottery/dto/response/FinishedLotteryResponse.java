package com.internship.juglottery.dto.response;

import java.util.List;

public record FinishedLotteryResponse (Long id, String eventName, List<WinnerResponse> winners) {
}
