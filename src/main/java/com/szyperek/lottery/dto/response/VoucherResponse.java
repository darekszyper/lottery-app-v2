package com.szyperek.lottery.dto.response;

import java.time.LocalDate;

public record VoucherResponse(
        Long id,
        String voucherName,
        String activationCode,
        LocalDate expirationDate
) {
}
