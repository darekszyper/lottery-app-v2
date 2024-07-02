package com.szyperek.lottery.dto.response;

public record WinnerResponse(ParticipantResponse participant, VoucherResponse voucher) {
}
