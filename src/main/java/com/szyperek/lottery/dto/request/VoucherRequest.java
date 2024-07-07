package com.szyperek.lottery.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoucherRequest {

    private Long userId;

    @NotBlank(message = "Voucher name required")
    @Size(max = 50, message = "Voucher name must be shorter than 50 letters")
    private String voucherName;

    @NotBlank(message = "Activation key required")
    @Size(max = 100, message = "Activation key must be shorter than 100 letters")
    private String activationCode;

    @NotNull(message = "Expiration date required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;
}
