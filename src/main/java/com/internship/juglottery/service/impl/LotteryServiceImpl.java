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
import com.internship.juglottery.service.*;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Setter
@RequiredArgsConstructor
@Transactional
public class LotteryServiceImpl implements LotteryService {

    private final LotteryRepo lotteryRepo;
    private final ParticipantService participantService;
    private final VoucherService voucherService;
    private final RandomizeService randomizeService;
    private final WinnerRepo winnerRepo;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public void changeLotteryStatusToActive(Long lotteryId) {
        lotteryRepo.changeStatusToActive(lotteryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Lottery> getAllNotActiveLotteries() {
        return lotteryRepo.findByStatus(Status.NOT_ACTIVE);
    }

    @Override
    public void changeLotteryStatusToFinished(Long lotteryId) {
        lotteryRepo.changeStatusToFinished(lotteryId);
    }
    @Override
    public void setCurrentDateWhenEventIsFinished(Long lotteryId) {
        lotteryRepo.setEventDate(lotteryId);
    }

    @Override
    public Lottery createLotteryWithAssignedVouchers(Lottery lottery, List<Voucher> vouchers) {
        Lottery savedLottery = lotteryRepo.save(lottery);
        vouchers.forEach(voucher -> voucher.setLottery(lottery));
        voucherService.saveAllVouchers(vouchers);
        return savedLottery;
    }

    @Override
    public boolean isLotteryStatusFinished(Long lotteryId) {
        Status status = lotteryRepo.getStatusFromDb(lotteryId);
        return status == Status.FINISHED;
    }


    @Override
    public List<Winner> pickWinners(Long lotteryId) {

        if (isLotteryStatusFinished(lotteryId)) {
            throw new LotteryIsFinishedException("Lottery is finished");
        }

        changeLotteryStatusToFinished(lotteryId);
        setCurrentDateWhenEventIsFinished(lotteryId);

        List<Participant> participants = participantService.getParticipantsByLotteryId(lotteryId);
        List<Voucher> vouchers = voucherService.getVouchersByLotteryId(lotteryId);
        List<Winner> winners = new ArrayList<>();
        Optional<Lottery> lotteryOptional = lotteryRepo.findById(lotteryId);
        Lottery lottery;

        if (participants.size() < vouchers.size()) {
            int z = vouchers.size();
            for (int i = z - 1; i >= participants.size(); i--) {
                vouchers.remove(i).setLottery(null);
            }
        }

        if (lotteryOptional.isPresent()) {
            lottery = lotteryOptional.get();

            for (Voucher voucher : vouchers) {
                int winnerIndex = randomizeService.randomize(participants.size());
                winners.add(new Winner(participants.get(winnerIndex), voucher, lottery));
                participants.remove(winnerIndex);
            }
        }

        List<Winner> savedWinners = winnerRepo.saveAll(winners);
        eventPublisher.publishEvent(new VouchersSentEvent(lotteryId));

        return savedWinners;
    }

    @Override
    public void deleteLottery(Long lotteryId) {
        lotteryRepo.deleteLottery(lotteryId);
    }

    @Override
    public List<Lottery> getAllLotteriesAssignedToUser(Long userId) {
        return lotteryRepo.findAllByAppUserIdAndStatus(userId, Status.NOT_ACTIVE);
    }
}
