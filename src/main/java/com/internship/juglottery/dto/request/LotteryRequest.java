package com.internship.juglottery.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LotteryRequest {

    @NotBlank(message = "Event name required")
    @Size(max = 50, message = "Event must be less than 50 characters")
    private String eventName;

    @NotBlank(message = "City required")
    @Size(max = 32, message = "City must be less than 50 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Only letters are allowed in the city")
    private String city;

    @Size(min = 1, message = "At least one voucher required")
    @NotNull(message = "You need to create a new voucher")
    private List<Long> voucherId;

    private Long userId;
}
