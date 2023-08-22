package com.internship.juglottery.event;

import com.internship.juglottery.entity.Participant;

public record RegistrationEmailEvent(Participant participant) {
}
