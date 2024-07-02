package com.szyperek.lottery.repository;

import com.szyperek.lottery.entity.Winner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WinnerRepo extends JpaRepository<Winner, Long> {

    List<Winner> findByLotteryId(Long lotteryId);
}
