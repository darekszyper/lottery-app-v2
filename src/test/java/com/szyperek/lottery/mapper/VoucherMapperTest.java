package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.entity.AppUser;
import com.szyperek.lottery.entity.Lottery;
import com.szyperek.lottery.entity.Voucher;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static com.szyperek.lottery.mapper.MapperTestUtils.getVoucher;
import static com.szyperek.lottery.mapper.MapperTestUtils.getVoucherRequest;

class VoucherMapperTest {

    VoucherMapper systemUnderTest;

    @BeforeEach
    void setUp() {
        systemUnderTest = new VoucherMapper();
    }

    @Test
    void shouldMapVoucherRequestToVoucher() {
        // given
        VoucherRequest voucherRequest = getVoucherRequest();
        Voucher voucher = getVoucher();

        // when
        systemUnderTest.mapToEntity(voucherRequest);

        // then

    }
}