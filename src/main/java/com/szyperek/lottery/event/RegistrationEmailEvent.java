package com.szyperek.lottery.event;

import com.szyperek.lottery.entity.Participant;

public record RegistrationEmailEvent(String contextPath, String token, Participant participant) {
}
