package com.internship.juglottery.service;

import com.internship.juglottery.entity.Voucher;

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
