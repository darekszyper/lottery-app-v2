package com.internship.juglottery.repository;

import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LotteryRepo extends JpaRepository<Lottery, Long> {

    @Query(value = "SELECT status FROM Lottery WHERE id=:id", nativeQuery = true)
    Status getStatusFromDb(Long id);

    @Modifying
    @Query(value = "UPDATE lottery Set status = 'ACTIVE' where id = :id", nativeQuery = true)
    void changeStatusToActive(Long id);

    @Modifying
    @Query(value = "UPDATE lottery Set lottery_date = NOW() where id = :id", nativeQuery = true)
    void setEventDate(Long id);

    @Modifying
    @Query(value = "UPDATE lottery Set status = 'FINISHED' where id = :id", nativeQuery = true)
    void changeStatusToFinished(Long id);

    @Modifying
    @Query(value = "DELETE FROM Lottery WHERE id = :id", nativeQuery = true)
    void deleteLottery(Long id);

    List<Lottery> findByStatus(Status status);

    List<Lottery> findAllByAppUserIdAndStatus(Long userId, Status status);
}
