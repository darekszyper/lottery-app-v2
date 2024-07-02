package com.szyperek.lottery.service.impl;

import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Participant;
import com.szyperek.lottery.entity.Voucher;
import com.szyperek.lottery.entity.Winner;
import com.szyperek.lottery.entity.enums.Status;
import com.szyperek.lottery.exception.LotteryNotActiveException;
import com.szyperek.lottery.repository.LotteryRepo;
import com.szyperek.lottery.repository.WinnerRepo;
import com.szyperek.lottery.service.LotteryService;
import com.szyperek.lottery.service.ParticipantService;
import com.szyperek.lottery.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepo lotteryRepo;
    private final ParticipantService participantService;
    private final VoucherService voucherService;
    private final RandomOrgServiceImpl randomOrgService;
    private final RandomizeServiceImpl randomizeService;
    private final WinnerRepo winnerRepo;

    @Override
    @Transactional
    public void changeLotteryStatusToActive(Long lotteryId) {
        if (lotteryRepo.getStatusFromDb(lotteryId) == Status.FINISHED) {
            throw new LotteryNotActiveException("Lottery is finished");
        }
        lotteryRepo.changeStatusToActive(lotteryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lottery> getAllNotActiveLotteries() {
        return lotteryRepo.findByStatus(Status.NOT_ACTIVE);
    }

    @Override
    @Transactional
    public void changeLotteryStatusToFinished(Long lotteryId) {
        lotteryRepo.changeStatusToFinished(lotteryId);
        setCurrentDateWhenEventIsFinished(lotteryId);
    }

    private void setCurrentDateWhenEventIsFinished(Long lotteryId) {
        lotteryRepo.setEventDate(lotteryId);
    }

    @Override
    @Transactional
    public Lottery createLotteryWithAssignedVouchers(Lottery lottery, List<Voucher> vouchers) {
        Lottery savedLottery = lotteryRepo.save(lottery);
        vouchers.forEach(voucher -> voucher.setLottery(lottery));
        voucherService.saveAllVouchers(vouchers);
        return savedLottery;
    }

    @Override
    @Transactional
    public List<Winner> pickWinners(Long lotteryId) {
        if (lotteryRepo.getStatusFromDb(lotteryId) != Status.ACTIVE) {
            throw new LotteryNotActiveException("Lottery is not active");
        }

        changeLotteryStatusToFinished(lotteryId);

        List<Participant> participants = participantService.getParticipantsByLotteryId(lotteryId);
        List<Voucher> vouchers = voucherService.getVouchersByLotteryId(lotteryId);
        List<Winner> winners = new ArrayList<>();
        Optional<Lottery> lotteryOptional = lotteryRepo.findById(lotteryId);

        if (participants.size() < vouchers.size())
            removeExcessVouchers(participants, vouchers);
        if (participants.isEmpty())
            return winners;

        if (lotteryOptional.isPresent()) {
            Lottery lottery = lotteryOptional.get();
            assignWinners(participants, vouchers, winners, lottery);
        }

        return winnerRepo.saveAll(winners);
    }

    private void removeExcessVouchers(List<Participant> participants, List<Voucher> vouchers) {
        int excessCount = vouchers.size() - participants.size();
        for (int i = vouchers.size() - 1; excessCount > 0; i--) {
            vouchers.remove(i).setLottery(null);
            excessCount--;
        }
    }

    private void assignWinners(List<Participant> participants, List<Voucher> vouchers,
                               List<Winner> winners, Lottery lottery) {
        List<Integer> winnersIndexes = randomOrgService.randomize(participants.size() - 1, vouchers.size());

        if (winnersIndexes == null) {
            winnersIndexes = randomizeService.randomize(participants.size() - 1, vouchers.size());
        }

        for (Integer winnersIndex : winnersIndexes) {
            winners.add(new Winner(participants.get(winnersIndex), vouchers.get(0), lottery));
            vouchers.remove(0);
        }
    }

    @Override
    @Transactional
    public void deleteLottery(Long lotteryId) {
        lotteryRepo.deleteLottery(lotteryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lottery> getAllLotteriesAssignedToUser(Long userId) {
        return lotteryRepo.findAllByAppUserIdAndStatus(userId, Status.NOT_ACTIVE);
    }

    @Override
    public List<Lottery> getAllFinishedLotteriesAssignedToUser(Long userId) {
        return lotteryRepo.findAllByAppUserIdAndStatus(userId, Status.FINISHED);
    }
}
