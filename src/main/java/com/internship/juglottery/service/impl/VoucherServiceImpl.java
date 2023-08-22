package com.internship.juglottery.service.impl;

import com.internship.juglottery.entity.Voucher;
import com.internship.juglottery.repository.VoucherRepo;
import com.internship.juglottery.service.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepo voucherRepo;

    @Override
    public List<Voucher> getVouchersByLotteryId(Long lotteryId) {
        return voucherRepo.findAllByLotteryId(lotteryId);
    }

    @Override
    public Voucher createVoucher(Voucher voucher) {
        return voucherRepo.save(voucher);
    }

    @Override
    public List<Voucher> saveAllVouchers(List<Voucher> vouchers) {
        return voucherRepo.saveAll(vouchers);
    }

    @Override
    public List<Voucher> getAllUnassignedVouchers() {
        return voucherRepo.findAllByLotteryId(null);
    }

    @Override
    public List<Voucher> getAllVouchersById(List<Long> idList) {
        return voucherRepo.findAllById(idList);
    }

    @Override
    public void removeVoucherId(Long lotteryId) {
        voucherRepo.removeVoucherByLotteryId(lotteryId);
    }

    @Override
    public List<Voucher> getAllNotUsedVouchersAssignedToUser(Long userId) {
        return voucherRepo.findAllByAppUserIdAndLotteryId(userId, null);
    }

}
