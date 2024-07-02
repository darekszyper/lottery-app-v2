package com.szyperek.lottery.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ParticipantResponse {

    Long id;
    String firstName;
    String email;

    @Override
    public String toString() {
        return firstName + ", " + email;
    }
}
