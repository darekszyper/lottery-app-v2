package com.szyperek.lottery.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmailRequest {

    @NotBlank(message = "Email required")
    @Email(message = "Email must be in correct format")
    private String email;
}
