package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Voucher;
import com.szyperek.lottery.entity.Winner;

import java.util.List;

public interface LotteryService {

    void changeLotteryStatusToActive(Long lotteryId);

    void changeLotteryStatusToFinished(Long lotteryId);

    List<Winner> pickWinners(Long lotteryId);

    List<Lottery> getAllNotActiveLotteries();

    Lottery createLotteryWithAssignedVouchers(Lottery lottery, List<Voucher> vouchers);

    void deleteLottery(Long lotteryId);

    List<Lottery> getAllLotteriesAssignedToUser(Long userId);

    List<Lottery> getAllFinishedLotteriesAssignedToUser(Long userId);
}
