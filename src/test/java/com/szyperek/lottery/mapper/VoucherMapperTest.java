package com.szyperek.lottery.mapper;

import com.szyperek.lottery.dto.request.VoucherRequest;
import com.szyperek.lottery.dto.response.VoucherResponse;
import com.szyperek.lottery.entity.Voucher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.szyperek.lottery.mapper.MapperTestUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class VoucherMapperTest {

    VoucherMapper voucherMapper;

    @BeforeEach
    void setUp() {
        voucherMapper = new VoucherMapper();
    }

    @Test
    void shouldMapVoucherRequestToVoucher() {
        // given
        VoucherRequest voucherRequest = getBasicVoucherRequest();
        Voucher expectedResult = getBasicVoucher();

        // when
        Voucher result = voucherMapper.mapToEntity(voucherRequest);

        // then
        assertEquals(expectedResult.getVoucherName(), result.getVoucherName());
        assertEquals(expectedResult.getActivationCode(), result.getActivationCode());
        assertEquals(expectedResult.getExpirationDate(), result.getExpirationDate());
        assertEquals(expectedResult.getAppUser().getId(), result.getAppUser().getId());
        assertNull(result.getLottery());
    }

    @Test
    void shouldReturnNullWhenVoucherRequestIsNull() {
        // when
        Voucher result = voucherMapper.mapToEntity(null);

        // then
        assertNull(result);
    }

    @Test
    void shouldMapVoucherToVoucherResponse() {
        // given
        Voucher voucher = getBasicVoucher();
        VoucherResponse expectedResult = getBasicVoucherResponse();

        // when
        VoucherResponse result = voucherMapper.mapToVoucherResponse(voucher);

        // then
        assertEquals(expectedResult.id(), result.id());
        assertEquals(expectedResult.voucherName(), result.voucherName());
        assertEquals(expectedResult.activationCode(), result.activationCode());
        assertEquals(expectedResult.expirationDate(), result.expirationDate());
    }

    @Test
    void shouldReturnNullWhenVoucherIsNull() {
        // when
        VoucherResponse result = voucherMapper.mapToVoucherResponse(null);

        // then
        assertNull(result);
    }
}