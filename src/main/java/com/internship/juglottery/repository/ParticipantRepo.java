package com.internship.juglottery.repository;

import com.internship.juglottery.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParticipantRepo extends JpaRepository<Participant, Long> {

    @Query("SELECT p FROM Participant p WHERE p.lottery.id = :lotteryId AND p.isEmailConfirmed = true")
    List<Participant> findByLotteryIdAndEmailConfirmed(Long lotteryId);

    @Modifying
    @Query(value = "DELETE FROM Participant WHERE lottery_id = :id", nativeQuery = true)
    void removeParticipantId(Long id);

    @Query(value = "SELECT id, email FROM participant WHERE email NOT LIKE '%*%'", nativeQuery = true)
    List<String> extractIdsAndEmails();

    @Modifying
    @Query(value = "UPDATE participant SET email = :email WHERE id = :id", nativeQuery = true)
    void editEmails(Long id, String email);

    @Query(value = "SELECT email FROM participant WHERE lottery_id = :id AND email = :email" , nativeQuery = true)
    String extractEmail(Long id, String email);
}
