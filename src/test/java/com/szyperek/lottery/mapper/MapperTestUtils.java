package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Voucher;
import com.szyperek.lottery.entity.enums.Role;
import com.szyperek.lottery.entity.enums.Status;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

public class MapperTestUtils {

    @NotNull
    static Voucher getVoucher() {
        return new Voucher(
                1L,
                "voucher",
                "12345",
                LocalDate.of(2100, 1, 1),
                null,
                getAppSuperUser()
        );
    }

    @NotNull
    private static Lottery getNotActiveLottery() {
        return new Lottery(
                1L,
                "lottery",
                "warsaw",
                LocalDate.of(2100, 1, 1),
                Status.NOT_ACTIVE,
                null,
                null,
                null,
                getAppSuperUser()
        );
    }

    @NotNull
    static AppUser getAppSuperUser() {
        return new AppUser(
                1L,
                "email@gmail.com",
                "name",
                "password",
                Role.SUPER_USER,
                null
        );
    }

    @NotNull
    static VoucherRequest getVoucherRequest() {
        return new VoucherRequest(
                "voucher",
                "12345",
                LocalDate.of(2100, 1, 1),
                1L
        );
    }
}
