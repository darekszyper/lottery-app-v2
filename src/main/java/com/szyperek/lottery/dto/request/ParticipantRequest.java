package com.szyperek.lottery.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantRequest {

    @NotNull(message = "Lottery id required")
    private Long lotteryId;

    @NotBlank(message = "First name required")
    @Size(max = 32, message = "Name must be shorter than 32 signs")
    private String firstName;

    @NotBlank(message = "Email required")
    @Email(message = "Email must be in correct format")
    @Pattern(regexp = "^([^*]+)$", message = "Email cannot contain *")
    private String email;
}
