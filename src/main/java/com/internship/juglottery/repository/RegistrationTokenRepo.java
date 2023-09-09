package com.internship.juglottery.repository;

import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationTokenRepo extends JpaRepository<RegistrationToken, Long> {

    RegistrationToken findByToken(String token);

    Optional<RegistrationToken> findByParticipant(Participant participant);
}
