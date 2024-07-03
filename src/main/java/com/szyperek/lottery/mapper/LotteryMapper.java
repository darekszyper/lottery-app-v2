package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.response.*;
import com.szyperek.lottery.entity.*;
import com.szyperek.lottery.entity.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class LotteryMapper {

    public LotteryResponse mapToResponse(Lottery lottery) {
        if (lottery == null) {
            return null;
        }

        Long id = null;
        String eventName = null;

        id = lottery.getId();
        eventName = lottery.getEventName();

        LotteryResponse lotteryResponse = new LotteryResponse(id, eventName);

        return lotteryResponse;
    }

    public Lottery mapToEntity(LotteryRequest lotteryRequest) {
        if (lotteryRequest == null) {
            return null;
        }

        Lottery lottery = new Lottery();

        setNotActiveStatus(lottery);

        lottery.setAppUser(lotteryRequestToAppUser(lotteryRequest));
        lottery.setEventName(lotteryRequest.getEventName());
        lottery.setCity(lotteryRequest.getCity());

        setVoucherListToNull(lottery);

        return lottery;
    }

    public FinishedLotteryResponse mapToFinishedLotteryResponse(Lottery lottery) {
        if (lottery == null) {
            return null;
        }

        Long id = null;
        String eventName = null;
        List<WinnerResponse> winners = null;
        LocalDate lotteryDate = null;
        String city = null;

        id = lottery.getId();
        eventName = lottery.getEventName();
        winners = winnerListToWinnerResponseList(lottery.getWinners());
        lotteryDate = lottery.getLotteryDate();
        city = lottery.getCity();

        FinishedLotteryResponse finishedLotteryResponse = new FinishedLotteryResponse(id, eventName, winners, lotteryDate, city);

        return finishedLotteryResponse;
    }

    protected AppUser lotteryRequestToAppUser(LotteryRequest lotteryRequest) {
        if (lotteryRequest == null) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setId(lotteryRequest.getUserId());

        return appUser;
    }

    protected ParticipantResponse participantToParticipantResponse(Participant participant) {
        if (participant == null) {
            return null;
        }

        ParticipantResponse participantResponse = new ParticipantResponse();

        participantResponse.setId(participant.getId());
        participantResponse.setFirstName(participant.getFirstName());
        participantResponse.setEmail(participant.getEmail());

        return participantResponse;
    }

    protected VoucherResponse voucherToVoucherResponse(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        Long id = null;
        String voucherName = null;
        String activationCode = null;
        LocalDate expirationDate = null;

        id = voucher.getId();
        voucherName = voucher.getVoucherName();
        activationCode = voucher.getActivationCode();
        expirationDate = voucher.getExpirationDate();

        VoucherResponse voucherResponse = new VoucherResponse(id, voucherName, activationCode, expirationDate);

        return voucherResponse;
    }

    protected WinnerResponse winnerToWinnerResponse(Winner winner) {
        if (winner == null) {
            return null;
        }

        ParticipantResponse participant = null;
        VoucherResponse voucher = null;

        participant = participantToParticipantResponse(winner.getParticipant());
        voucher = voucherToVoucherResponse(winner.getVoucher());

        WinnerResponse winnerResponse = new WinnerResponse(participant, voucher);

        return winnerResponse;
    }

    protected List<WinnerResponse> winnerListToWinnerResponseList(List<Winner> list) {
        if (list == null) {
            return null;
        }

        List<WinnerResponse> list1 = new ArrayList<WinnerResponse>(list.size());
        for (Winner winner : list) {
            list1.add(winnerToWinnerResponse(winner));
        }

        return list1;
    }

    protected void setNotActiveStatus(Lottery lottery) {
        lottery.setStatus(Status.NOT_ACTIVE);
    }

    protected void setVoucherListToNull(Lottery lottery) {
        lottery.setVouchers(null);
    }
}

