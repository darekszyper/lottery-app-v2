package com.szyperek.lottery.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantResponse {

    private Long id;
    private String firstName;
    private String email;

    @Override
    public String toString() {
        return firstName + ", " + email;
    }
}
