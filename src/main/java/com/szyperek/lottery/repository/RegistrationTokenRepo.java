package com.szyperek.lottery.repository;

import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.RegistrationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationTokenRepo extends JpaRepository<RegistrationToken, Long> {

    RegistrationToken findByToken(String token);

    Optional<RegistrationToken> findByParticipant(Participant participant);
}
