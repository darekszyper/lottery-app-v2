package com.internship.juglottery.dto.response;

import java.time.LocalDate;

public record VoucherResponse (Long id, String voucherName, String activationCode, LocalDate expirationDate){
}
