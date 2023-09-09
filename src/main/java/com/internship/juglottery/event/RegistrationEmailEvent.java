package com.internship.juglottery.event;

import com.internship.juglottery.entity.Participant;

public record RegistrationEmailEvent(String contextPath, String token, Participant participant) {
}
