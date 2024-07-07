package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.AppUserRequest;
import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.dto.response.VoucherResponse;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Voucher;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.entity.enums.Status;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

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
    private static Lottery getBasicNotActiveLottery() {
        Lottery lottery = new Lottery();
        lottery.setId(1L);
        lottery.setEventName("lottery");
        lottery.setCity("warsaw");
        lottery.setLotteryDate(LocalDate.of(2100, 1, 1));
        lottery.setStatus(Status.NOT_ACTIVE);
        lottery.setAppUser(getBasicAppSuperUser());
        return lottery;
    }

    @NotNull
    static AppUser getBasicAppSuperUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("email@gmail.com");
        appUser.setName("name");
        appUser.setPassword("password");
        appUser.setRole(Role.SUPER_USER);
        return appUser;
    }

    @NotNull
    static AppUser getBasicAppUser() {
        AppUser appUser = new AppUser();
        appUser.setId(1L);
        appUser.setEmail("email@gmail.com");
        appUser.setName("name");
        appUser.setPassword("password");
        appUser.setRole(Role.USER);
        return appUser;
    }

    @NotNull
    static AppUserRequest getBasicAppUserRequest() {
        return new AppUserRequest(
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
}
