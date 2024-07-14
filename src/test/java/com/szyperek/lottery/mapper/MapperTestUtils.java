package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.RegistrationRequest;
import com.szyperek.lottery.dto.request.LotteryRequest;
import com.szyperek.lottery.dto.request.ParticipantRequest;
import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.dto.response.*;
import com.szyperek.lottery.entity.*;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.entity.enums.Status;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.Collections;

public class MapperTestUtils {

    @NotNull
    static Voucher getBasicVoucher() {
        Voucher voucher = new Voucher();
        voucher.setId(1L);
        voucher.setVoucherName("voucher");
        voucher.setActivationCode("12345");
        voucher.setExpirationDate(LocalDate.of(2100, 1, 1));
        voucher.setAppUser(getBasicAppSuperUser());
        return voucher;
    }

    @NotNull
    static Lottery getBasicLottery() {
        Lottery lottery = new Lottery();
        lottery.setId(1L);
        lottery.setEventName("lottery");
        lottery.setCity("Warsaw");
        lottery.setLotteryDate(LocalDate.of(2100, 1, 1));
        lottery.setAppUser(getBasicAppSuperUser());
        return lottery;
    }

    @NotNull
    static Lottery getBasicNotActiveLottery() {
        Lottery lottery = getBasicLottery();
        lottery.setStatus(Status.NOT_ACTIVE);
        return lottery;
    }

    @NotNull
    static Lottery getBasicFinishedLottery() {
        Lottery lottery = getBasicLottery();
        lottery.setStatus(Status.FINISHED);
        lottery.setWinners(Collections.emptyList());
        return lottery;
    }

    @NotNull
    static LotteryResponse getBasicLotteryResponse() {
        return new LotteryResponse(
                1L,
                "lottery"
        );
    }

    @NotNull
    static FinishedLotteryResponse getBasicFinishedLotteryResponse() {
        Lottery lottery = getBasicFinishedLottery();
        return new FinishedLotteryResponse(
                lottery.getId(),
                lottery.getEventName(),
                Collections.emptyList(),
                lottery.getLotteryDate(),
                lottery.getCity()
        );
    }

    @NotNull
    static LotteryRequest getBasicLotteryRequest() {
        return new LotteryRequest(
                "lottery",
                "Warsaw",
                Collections.emptyList(),
                1L
        );
    }

    @NotNull
    static AppUser getBasicAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("email@gmail.com");
        appUser.setName("name");
        appUser.setPassword("encodedPassword");
        appUser.setRole(Role.USER);
        return appUser;
    }

    @NotNull
    static AppUser getBasicAppSuperUser() {
        AppUser appUser = getBasicAppUser();
        appUser.setRole(Role.SUPER_USER);
        return appUser;
    }

    @NotNull
    static RegistrationRequest getBasicAppUserRequest() {
        return new RegistrationRequest(
                "email@gmail.com",
                "name"
        );
    }

    @NotNull
    static AppUserResponse getBasicAppUserResponse() {
        return new AppUserResponse(
                1L,
                "email@gmail.com",
                "name"
        );
    }

    @NotNull
    static VoucherRequest getBasicVoucherRequest() {
        return new VoucherRequest(
                1L,
                "voucher",
                "12345",
                LocalDate.of(2100, 1, 1)
        );
    }

    @NotNull
    static VoucherResponse getBasicVoucherResponse() {
        return new VoucherResponse(
                1L,
                "voucher",
                "12345",
                LocalDate.of(2100, 1, 1)
        );
    }

    @NotNull
    static Participant getBasicParticipant() {
        Participant participant = new Participant();
        participant.setId(1L);
        participant.setFirstName("name");
        participant.setEmail("email@gmail.com");
        participant.setEmailConfirmed(true);
        participant.setLottery(getBasicFinishedLottery());
        return participant;
    }

    @NotNull
    static ParticipantRequest getBasicParticipantRequest() {
        Participant participant = getBasicParticipant();
        return  new ParticipantRequest(
                participant.getLottery().getId(),
                participant.getFirstName(),
                participant.getEmail()
        );
    }

    @NotNull
    static ParticipantResponse getBasicParticipantResponse() {
        Participant participant = getBasicParticipant();
        return  new ParticipantResponse(
                participant.getLottery().getId(),
                participant.getFirstName(),
                participant.getEmail()
        );
    }

    @NotNull
    static Winner getBasicWinner() {
        Winner winner = new Winner();
        winner.setId(1L);
        winner.setParticipant(getBasicParticipant());
        winner.setVoucher(getBasicVoucher());
        winner.setLottery(getBasicLottery());
        return  winner;
    }
}
