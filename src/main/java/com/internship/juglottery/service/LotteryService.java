package com.internship.juglottery.service;

import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.entity.Winner;

import java.util.List;

public interface LotteryService {

    void changeLotteryStatusToActive(Long lotteryId);

    void changeLotteryStatusToFinished(Long lotteryId);

    List<Winner> pickWinners(Long lotteryId);

    List<Lottery> getAllNotActiveLotteries();

    Lottery createLotteryWithAssignedVouchers(Lottery lottery, List<Voucher> vouchers);

    void deleteLottery(Long lotteryId);

    List<Lottery> getAllLotteriesAssignedToUser(Long userId);
}
