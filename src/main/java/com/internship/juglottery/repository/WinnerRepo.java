package com.internship.juglottery.repository;

import com.internship.juglottery.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinnerRepo extends JpaRepository<Winner, Long> {

    List<Winner> findByLotteryId(Long lotteryId);
}
