package com.szyperek.lottery.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegistrationRequest {

    @NotBlank(message = "Email required")
    @Email(message = "Email must be in correct format")
    private String email;

    @NotBlank(message = "Name required")
    @Size(max = 32, message = "Name must be shorter than 32 signs")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Only letters are allowed in the name")
    private String name;
}
