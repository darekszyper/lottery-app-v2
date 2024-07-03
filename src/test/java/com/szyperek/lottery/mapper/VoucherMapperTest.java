package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.entity.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.szyperek.lottery.mapper.MapperTestUtils.getVoucher;
import static com.szyperek.lottery.mapper.MapperTestUtils.getVoucherRequest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        Voucher result = systemUnderTest.mapToEntity(voucherRequest);

        // then
        assertNotNull(result);
    }
}