package com.internship.juglottery.repository;

import com.internship.juglottery.entity.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VoucherRepo extends JpaRepository<Voucher, Long> {

    List<Voucher> findAllByLotteryId(Long lotteryId);

    @Modifying
    @Query(value = "UPDATE voucher Set lottery_id = NULL where lottery_id = :lotteryId", nativeQuery = true)
    void removeVoucherByLotteryId(Long lotteryId);

    List<Voucher> findAllByAppUserIdAndLotteryId(Long userId, Long lotteryId);
}
