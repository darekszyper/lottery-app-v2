package com.internship.juglottery.service;

import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.repository.VoucherRepo;
import com.internship.juglottery.service.impl.VoucherServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class VoucherServiceTest {

    @Mock
    VoucherRepo voucherRepo;
    @Mock
    Voucher voucher;

    @InjectMocks
    VoucherServiceImpl voucherServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should call method findAllByLotteryId")
    void shouldCallMethodFindAllByLotteryId() {
        //given
        Long lotteryId = 1L;

        //when
        voucherServiceImpl.getVouchersByLotteryId(lotteryId);

        //then
        verify(voucherRepo, times(1)).findAllByLotteryId(lotteryId);
    }

    @Test
    @DisplayName("Should call method save")
    void shouldCallMethodSave() {
        //given
        Voucher voucher = new Voucher();

        //when
        voucherServiceImpl.createVoucher(voucher);

        //then
        verify(voucherRepo, times(1)).save(voucher);
    }

    @Test
    @DisplayName("Should call findAll method")
    void shouldCallFindAllMethod() {
        //when
        voucherServiceImpl.getAllUnassignedVouchers();

        //then
        verify(voucherRepo, times(1)).findAllByLotteryId(null);
    }

    @Test
    @DisplayName("Should call findAllById method")
    void shouldCallFindAllByIdMethod() {
        //given
        List<Long> idList = Arrays.asList(1L, 2L, 3L, 4L);

        //when
        voucherServiceImpl.getAllVouchersById(idList);

        //then
        verify(voucherRepo, times(1)).findAllById(idList);
    }

    @Test
    @DisplayName("Should call saveAll method")
    void shouldCallSaveAllMethod() {
        //given
        List<Voucher> vouchers = new ArrayList<>();
        vouchers.add(new Voucher("Voucher1", LocalDate.of(2023, 1, 2)));
        vouchers.add(new Voucher("Voucher2", LocalDate.of(2023, 1, 4)));

        //when
        voucherServiceImpl.saveAllVouchers(vouchers);

        //then
        verify(voucherRepo, times(1)).saveAll(vouchers);
    }

    @Test
    @DisplayName("Should call removeVoucherId method")
    void shouldCallRemoveVoucherId() {
        //given
        Long lotteryId = 1L;

        //when
        voucherServiceImpl.removeVoucherId(lotteryId);

        //then
        verify(voucherRepo, times(1)).removeVoucherByLotteryId(lotteryId);
    }

    @Test
    @DisplayName("Should return list of not used vouchers created by user")
    void shouldReturnAllNotUsedVouchersAssignedToUser() {
        //given
        Long userId = 1L;
        when(voucherRepo.findAllByAppUserIdAndLotteryId(userId, null)).thenReturn(List.of(voucher));

        //when
        List<Voucher> vouchers = voucherServiceImpl.getAllNotUsedVouchersAssignedToUser(userId);

        //then
        assertNotNull(vouchers);
    }
}