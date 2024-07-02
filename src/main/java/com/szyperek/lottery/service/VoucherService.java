package com.szyperek.lottery.service;

import com.szyperek.lottery.entity.Voucher;

import java.util.List;

public interface VoucherService {

    List<Voucher> getVouchersByLotteryId(Long lotteryId);

    Voucher createVoucher(Voucher voucher);

    List<Voucher> getAllUnassignedVouchers();

    List<Voucher> getAllVouchersById(List<Long> id);

    List<Voucher> saveAllVouchers(List<Voucher> vouchers);

    void removeVoucherId(Long lotteryId);

    List<Voucher> getAllNotUsedVouchersAssignedToUser(Long userId);
}
