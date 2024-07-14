package com.szyperek.lottery.repository;

import com.szyperek.lottery.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);
}
