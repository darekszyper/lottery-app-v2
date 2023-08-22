package com.internship.juglottery.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResetPasswordRequest {

    private String token;

    @Size(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
}
