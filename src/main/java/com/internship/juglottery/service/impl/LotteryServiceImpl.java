package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.Lottery;
import com.internship.juglottery.entity.Participant;
import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.entity.Winner;
import com.internship.juglottery.entity.enums.Status;
import com.internship.juglottery.event.VouchersSentEvent;
import com.internship.juglottery.exception.LotteryIsFinishedException;
import com.internship.juglottery.repository.LotteryRepo;
import com.internship.juglottery.repository.WinnerRepo;
import com.internship.juglottery.service.LotteryService;
import com.internship.juglottery.service.ParticipantService;
import com.internship.juglottery.service.RandomizeService;
import com.internship.juglottery.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
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
    private final RandomizeService randomizeService;
    private final WinnerRepo winnerRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public void changeLotteryStatusToActive(Long lotteryId) {
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
    @Transactional(readOnly = true)
    public boolean isLotteryStatusFinished(Long lotteryId) {
        Status status = lotteryRepo.getStatusFromDb(lotteryId);
        return status == Status.FINISHED;
    }

    @Override
    @Transactional
    public List<Winner> pickWinners(Long lotteryId) {
        if (isLotteryStatusFinished(lotteryId)) {
            throw new LotteryIsFinishedException("Lottery is finished");
        }

        changeLotteryStatusToFinished(lotteryId);

        List<Participant> participants = participantService.getParticipantsByLotteryId(lotteryId);
        List<Voucher> vouchers = voucherService.getVouchersByLotteryId(lotteryId);
        List<Winner> winners = new ArrayList<>();
        Optional<Lottery> lotteryOptional = lotteryRepo.findById(lotteryId);

        if (participants.size() < vouchers.size()) {
            removeExcessVouchers(participants, vouchers);
        }

        if (lotteryOptional.isPresent()) {
            Lottery lottery = lotteryOptional.get();
            assignWinners(participants, vouchers, winners, lottery);
        }

        List<Winner> savedWinners = winnerRepo.saveAll(winners);
        eventPublisher.publishEvent(new VouchersSentEvent(lotteryId));
        return savedWinners;
    }

    private void removeExcessVouchers(List<Participant> participants, List<Voucher> vouchers) {
        int excessCount = vouchers.size() - participants.size();
        for (int i = vouchers.size() - 1; i >= excessCount; i--) {
            vouchers.remove(i).setLottery(null);
        }
    }

    private void assignWinners(List<Participant> participants, List<Voucher> vouchers,
                               List<Winner> winners, Lottery lottery) {

        List<Integer> winnersIndexes = randomizeService.randomize(participants.size() - 1, vouchers.size());

        for (int i : winnersIndexes) {
            winners.add(new Winner(participants.get(winnersIndexes.get(i)), vouchers.get(0), lottery));
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
}
