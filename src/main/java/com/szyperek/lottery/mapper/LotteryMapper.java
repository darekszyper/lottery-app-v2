package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.response.*;
import com.szyperek.lottery.entity.*;
import com.szyperek.lottery.entity.enums.Status;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class LotteryMapper {

    public LotteryResponse mapToResponse(Lottery lottery) {
        if (lottery == null) {
            return null;
        }

        Long id = lottery.getId();
        String eventName = lottery.getEventName();

        return new LotteryResponse(id, eventName);
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

        Long id = lottery.getId();
        String eventName = lottery.getEventName();
        List<WinnerResponse> winners = winnerListToWinnerResponseList(lottery.getWinners());
        LocalDate lotteryDate = lottery.getLotteryDate();
        String city = lottery.getCity();

        return new FinishedLotteryResponse(id, eventName, winners, lotteryDate, city);
    }

    private AppUser lotteryRequestToAppUser(LotteryRequest lotteryRequest) {
        if (lotteryRequest == null) {
            return null;
        }

        AppUser appUser = new AppUser();

        appUser.setId(lotteryRequest.getUserId());

        return appUser;
    }

    private ParticipantResponse participantToParticipantResponse(Participant participant) {
        if (participant == null) {
            return null;
        }

        ParticipantResponse participantResponse = new ParticipantResponse();

        participantResponse.setId(participant.getId());
        participantResponse.setFirstName(participant.getFirstName());
        participantResponse.setEmail(participant.getEmail());

        return participantResponse;
    }

    private VoucherResponse voucherToVoucherResponse(Voucher voucher) {
        if (voucher == null) {
            return null;
        }

        Long id = voucher.getId();
        String voucherName = voucher.getVoucherName();
        String activationCode = voucher.getActivationCode();
        LocalDate expirationDate = voucher.getExpirationDate();

        return new VoucherResponse(id, voucherName, activationCode, expirationDate);
    }

    private WinnerResponse winnerToWinnerResponse(Winner winner) {
        if (winner == null) {
            return null;
        }

        ParticipantResponse participant = participantToParticipantResponse(winner.getParticipant());
        VoucherResponse voucher = voucherToVoucherResponse(winner.getVoucher());

        return new WinnerResponse(participant, voucher);
    }

    private List<WinnerResponse> winnerListToWinnerResponseList(List<Winner> list) {
        if (list == null) {
            return Collections.emptyList();
        }

        List<WinnerResponse> list1 = new ArrayList<>(list.size());
        for (Winner winner : list) {
            list1.add(winnerToWinnerResponse(winner));
        }

        return list1;
    }

    private void setNotActiveStatus(Lottery lottery) {
        lottery.setStatus(Status.NOT_ACTIVE);
    }

    private void setVoucherListToNull(Lottery lottery) {
        lottery.setVouchers(null);
    }
}

